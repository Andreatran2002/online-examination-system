package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAO implements DAO<Course> {
    TeacherDAO teacherDAO ;
    public CourseDAO(){
        teacherDAO = new TeacherDAO();
    }
    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public Optional<Course> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(Course course) {
        Integer courseId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Course (teacher_id, name , description, start , end , created_at , category) VALUES ( ?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            Teacher teacher = teacherDAO.getByUserId(AppUtils.CURRENT_USER.getId());
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setString(2, course.getName());
            preparedStatement.setString(3, course.getDescription());
            preparedStatement.setTimestamp(4, course.getStart());
            preparedStatement.setTimestamp(5, course.getEnd());
            preparedStatement.setTimestamp(6, AppUtils.getCurrentDateTime());
            preparedStatement.setString(7, course.getCategory());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                courseId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return courseId;
    }

    @Override
    public void update(Course course) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Course SET name=? , description=? , start=? , end=?, category=?, updated_at=? WHERE id=? ")) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setTimestamp(3, course.getStart());
            preparedStatement.setTimestamp(4, course.getEnd());
            preparedStatement.setString(5, course.getCategory());
            preparedStatement.setTimestamp(6, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(7, course.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(Course course) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Course SET deleted_at=? WHERE id=? ")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(1, course.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    public ObservableList<Course> getByTeacherId(Integer id ){
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Course WHERE teacher_id=? AND deleted_at IS NULL ")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Course course = new Course();
                // TODO: lay thong tin User
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setDeleted_at(rs.getTimestamp("deleted_at"));
                course.setStart(rs.getTimestamp("start"));
                course.setEnd(rs.getTimestamp("end"));
                course.setCategory(rs.getString("category"));
                courses.add(course);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return courses;
    }
}
