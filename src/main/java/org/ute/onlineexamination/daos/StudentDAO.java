package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;

import java.sql.*;
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
    public Integer save(Student student) {
        Integer studentId =-1;
        try (Connection connection = DBConnectionFactory.getConnection();
             // TODO: check email sau do create user va account student hoac teacher
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Student (user_id) VALUES ( ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, student.getUser_id());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                studentId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return  studentId;
    }

    @Override
    public void update(Student student) {

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
