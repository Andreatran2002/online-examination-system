package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.StudentUser;
import org.ute.onlineexamination.models.TeacherUser;
import org.ute.onlineexamination.utils.AppUtils;



import java.sql.*;
import java.util.Optional;

public class StudentUserDAO implements DAO<StudentUser> {

    @Override
    public ObservableList<StudentUser> getAll() {
        ObservableList<StudentUser> studentUserList = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT s.id,s.user_id, u.full_name, u.mobile, u.email, u.created_at, u.updated_at, u.last_login, u.deleted_at  FROM User u inner join Student s on u.id = s.user_id and u.is_admin = '0'")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int user_id = rs.getInt("user_id");
                String full_name = rs.getString("full_name");
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
    public Optional<StudentUser> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(StudentUser studentUser) {
        return 0;
    }

    @Override
    public void update(StudentUser studentUser) {

    }

    public int countStudents() {
        int count = 0;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS count FROM User u INNER JOIN Student s ON u.id = s.user_id WHERE u.is_admin = '0'")) {
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
    public void delete(StudentUser studentUser) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Student s JOIN User u ON s.user_id = u.id SET u.deleted_at = ?, s.deleted_at=? WHERE s.id=?")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setTimestamp(2, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(3, studentUser.getId() );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    public void restore(StudentUser studentUser) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Student s JOIN User u ON s.user_id = u.id SET u.deleted_at = NULL, s.deleted_at=NULL WHERE s.id=?")) {
            preparedStatement.setInt(1, studentUser.getId() );
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }
}
