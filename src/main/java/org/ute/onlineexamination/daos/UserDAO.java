package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserDAO implements DAO<User> {

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<User> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(User user) {
        Integer userId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             // TODO: check email sau do create user va account student hoac teacher
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (full_name, email, password_hash) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFull_name());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword_hash());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                userId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return userId;
    }

    @Override
    public void update(User user) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE User SET last_login=? , full_name=? , updated_at=? , password_hash=?, mobile=? WHERE id=? ", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, user.getLast_login());
            preparedStatement.setString(2, user.getFull_name());
            preparedStatement.setTimestamp(3, AppUtils.getCurrentDateTime());
            preparedStatement.setString(4, user.getPassword_hash());
            preparedStatement.setString(5, user.getMobile());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(User user) {

    }

    public User getByEmail(String email ){
        User user = new User();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User WHERE email=? ")) {
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                // TODO: lay thong tin User
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setCreated_at(rs.getTimestamp("created_at"));
                user.setUpdated_at(rs.getTimestamp("updated_at"));
                user.setDeleted_at(rs.getTimestamp("deleted_at"));
                user.setFull_name(rs.getString("full_name"));
                user.setLast_login(rs.getTimestamp("last_login"));
                user.setIs_admin(rs.getBoolean("is_admin"));
                user.setPassword_hash(rs.getString("password_hash"));
                user.setMobile(rs.getString("mobile"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return user;
    }
}
