package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.TeacherUser;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.Optional;

public class TeacherUserDAO implements DAO<TeacherUser> {


    @Override
    public ObservableList<TeacherUser> getAll() {
        ObservableList<TeacherUser> teacherUserList = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.id,t.user_id, u.full_name, u.mobile, u.email, t.title, t.address ,u.created_at, u.updated_at, u.last_login, u.deleted_at  FROM User u inner join Teacher t on u.id = t.user_id and u.is_admin = '0'")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                String full_name = rs.getString("full_name");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                String title = rs.getString("title");
                String address = rs.getString("address");
                Timestamp created_at = rs.getTimestamp("created_at");
                Timestamp updated_at = rs.getTimestamp("updated_at");
                Timestamp last_login = rs.getTimestamp("last_login");
                Timestamp deleted_at = rs.getTimestamp("deleted_at");
                TeacherUser teacherUser = new TeacherUser(id, user_id, full_name, mobile, email, title, address, created_at, updated_at, last_login, deleted_at);
                teacherUserList.add(teacherUser);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return teacherUserList;
    }
    public int countTeachers() {
        int count = 0;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM User u INNER JOIN Teacher t ON u.id = t.user_id WHERE u.is_admin = '0'")) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return count;
    }


    @Override
    public Optional<TeacherUser> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(TeacherUser teacherUser) {
        return 0;
    }

    @Override
    public void update(TeacherUser teacherUser) {

    }

    @Override
    public void delete(TeacherUser teacherUser) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Teacher t JOIN User u ON t.user_id = u.id SET u.deleted_at = ?, t.deleted_at=? WHERE t.id=?")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setTimestamp(2, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(3, teacherUser.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }
    public void restore(TeacherUser teacherUser) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Teacher t JOIN User u ON t.user_id = u.id SET u.deleted_at = NULL, t.deleted_at=NULL WHERE t.id=?")) {
            preparedStatement.setInt(1, teacherUser.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }
}
