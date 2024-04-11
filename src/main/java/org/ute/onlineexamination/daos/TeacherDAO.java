package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
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
        return Optional.empty();
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
                teacher.setCreated_at(rs.getTime("created_at"));
                teacher.setUpdated_at(rs.getTime("updated_at"));
                teacher.setDeleted_at(rs.getTime("deleted_at"));
                teacher.setTitle(rs.getString("title"));
                teacher.setAddress(rs.getString("address"));
                teacher.setUser_id(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return teacher;
    }
}
