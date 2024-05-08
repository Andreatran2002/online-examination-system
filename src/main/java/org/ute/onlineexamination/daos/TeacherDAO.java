package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.*;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TeacherDAO implements DAO<Teacher> {


    @Override
    public List<Teacher> getAll() {
        return null;
    }

    @Override
    public Optional<Teacher> get(int id) {
        Teacher teacher = new Teacher();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Teacher WHERE id=? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                teacher.setId(rs.getInt("id"));
                teacher.setAddress(rs.getString("address"));
                teacher.setTitle(rs.getString("title"));
                teacher.setUser_id(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return Optional.of(teacher);
    }

    @Override
    public Integer save(Teacher teacher) {
        Integer teacherId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             // TODO: check email sau do create user va account student hoac teacher
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Teacher (user_id,created_at) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, teacher.getUser_id());
            preparedStatement.setTimestamp(2, AppUtils.getCurrentDateTime());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                teacherId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return teacherId;
    }



    @Override
    public void update(Teacher teacher) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Teacher SET updated_at=?, title=?, address=? WHERE user_id=? ")) {
            preparedStatement.setTimestamp(1,AppUtils.getCurrentDateTime());
            preparedStatement.setString(2, teacher.getTitle());
            preparedStatement.setString(3,teacher.getAddress());
            preparedStatement.setInt(4, teacher.getUser_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(Teacher teacher) {

    }

    public Teacher getByUserId(Integer id){
        Teacher teacher = new Teacher();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Teacher WHERE user_id=? ")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                // TODO: lay thong tin User
                teacher.setId(rs.getInt("id"));
                teacher.setCreated_at(rs.getTimestamp("created_at"));
                teacher.setUpdated_at(rs.getTimestamp("updated_at"));
                teacher.setDeleted_at(rs.getTimestamp("deleted_at"));
                teacher.setTitle(rs.getString("title"));
                teacher.setAddress(rs.getString("address"));
                teacher.setUser_id(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return teacher;
    }

    public Integer getTotalStudent(Integer teacher_id){
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(DISTINCT cr.student_id) AS student_count\n" +
                     "FROM Course c\n" +
                     "JOIN CourseRegistration cr ON c.id = cr.course_id\n" +
                     "WHERE c.teacher_id = ?")) {
            preparedStatement.setInt(1, teacher_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return 0 ;
    }

    public ObservableList<XYChart.Series> getExamPerCourse(Integer teacher_id){
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.name AS course_name, COUNT(e.id) AS exam_count\n" +
                     "    FROM Course c\n" +
                     "    LEFT JOIN Examination e ON c.id = e.course_id AND e.deleted_at IS NULL\n" +
                     "    WHERE c.teacher_id =? AND c.deleted_at IS NULL \n" +
                     "    GROUP BY c.id, c.name;")) {
            preparedStatement.setInt(1, teacher_id );
            ResultSet rs = preparedStatement.executeQuery();
            Integer i = 1;
            while (rs.next()){
                XYChart.Series series = new XYChart.Series();
                series.setName(rs.getString("course_name"));
                series.getData().add(new XYChart.Data(rs.getString("course_name"), rs.getDouble("exam_count")));
                data.add(series);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return data;
    }

    public ObservableList<XYChart.Series> getStudentPerCourse(Integer teacher_id){
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.name AS course_name, COUNT(cr.student_id) AS student_count\n" +
                     "FROM Course c\n" +
                     "LEFT JOIN CourseRegistration cr ON c.id = cr.course_id AND cr.deleted_at IS NULL\n" +
                     "WHERE c.teacher_id = ? AND c.deleted_at IS NULL\n" +
                     "GROUP BY c.id, c.name\n")) {
            preparedStatement.setInt(1, teacher_id );
            ResultSet rs = preparedStatement.executeQuery();
            Integer i = 1;
            while (rs.next()){
                XYChart.Series series = new XYChart.Series();
                series.setName(rs.getString("course_name"));
                series.getData().add(new XYChart.Data(rs.getString("course_name"), rs.getDouble("student_count")));
                data.add(series);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return data;
    }

}
