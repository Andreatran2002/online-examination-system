package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentDAO implements DAO<Student> {


    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public Optional<Student> get(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Student student) {
        try (Connection connection = DBConnectionFactory.getConnection();
             // TODO: check email sau do create user va account student hoac teacher
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Student (user_id) VALUES ( ?)")) {
            preparedStatement.setInt(1, student.getUser_id());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void update(Student student) {

    }


    public User updateStudentByEmail(String email) {
        User user = new User();
        try (Connection connection = DBConnectionFactory.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Student JOIN User ON Student.user_id = User.id SET last_login=? , full_name=? , updated_at=? , password_hash=?, mobile=? WHERE email=?  ")){
            preparedStatement.setTimestamp(1, user.getLast_login());
            preparedStatement.setString(2, user.getFull_name());
            preparedStatement.setTimestamp(3, AppUtils.getCurrentDateTime());
            preparedStatement.setString(4, user.getPassword_hash());
            preparedStatement.setString(5, user.getMobile());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return user;
    }

    public User getStudentEmail(String email) {
        User user = new User();
        Student student = new Student();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT User.email FROM User JOIN Student ON User.id = ?")){
            preparedStatement.setInt(1, student.getUser_id());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                user.setEmail(rs.getString("email"));
            }
        }catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return user;
    }


    @Override
    public void delete(Student student) {

    }
    public Student getByUserId(Integer id){
        Student student = new Student();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Student WHERE user_id=? ")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                // TODO: lay thong tin User
                student.setId(rs.getInt("id"));
                student.setCreated_at(rs.getTime("created_at"));
                student.setUpdated_at(rs.getTime("updated_at"));
                student.setDeleted_at(rs.getTime("deleted_at"));
                student.setUser_id(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return student;
    }
}
