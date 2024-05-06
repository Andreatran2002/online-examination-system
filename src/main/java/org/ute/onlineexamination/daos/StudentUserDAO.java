package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.StudentUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentUserDAO implements DAO<StudentUserDAO>{

    @Override
    public ObservableList<StudentUser> getAll() {
        ObservableList<StudentUser> studentUserList = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT s.id,s.user_id, u.full_name, u.mobile, u.email, u.created_at, u.update_at, u.last_login, u.deleted_at  FROM User u inner join Student s on u.id = s.user_id")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("StudentId");
                int user_id = rs.getInt("UserId");
                String full_name = rs.getString("fullName");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                Timestamp created_at = rs.getTimestamp("created_at");
                Timestamp updated_at = rs.getTimestamp("updated_at");
                Timestamp last_login = rs.getTimestamp("last_login");
                Timestamp deleted_at = rs.getTimestamp("deleted_at");
                StudentUser studentUser = new StudentUser(id, user_id, full_name, mobile, email, created_at, updated_at, last_login, deleted_at);
                studentUserList.add(studentUser);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return studentUserList;
    }

    @Override
    public Optional<StudentUserDAO> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(StudentUserDAO studentUserDAO) {
        return 0;
    }

    @Override
    public void update(StudentUserDAO studentUserDAO) {

    }

    @Override
    public void delete(StudentUserDAO studentUserDAO) {

    }
}
