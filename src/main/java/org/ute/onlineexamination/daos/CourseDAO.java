package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void save(Course course) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Course (teacher_id, name , description, start , end , created_at , category) VALUES ( ?,?,?,?,?,?,?)")) {
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
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(Course course) {

    }

    public ObservableList<Course> getByTeacherId(Integer id ){
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Course WHERE teacher_id=? ")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Course course = new Course();
                // TODO: lay thong tin User
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("name"));
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
