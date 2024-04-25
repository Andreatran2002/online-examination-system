package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.TakeExam;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TakeExamDAO implements DAO<TakeExam> {
    @Override
    public List<TakeExam> getAll() {
        return null;
    }

    @Override
    public Optional<TakeExam> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(TakeExam takeExam) {
        Integer takeId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TakeExam (student_id, exam_id, start, created_at) VALUES ( ?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, takeExam.getStudent_id());
            preparedStatement.setInt(2, takeExam.getExam_id());
            preparedStatement.setTimestamp(3,AppUtils.getCurrentDateTime());
            preparedStatement.setTimestamp(4, AppUtils.getCurrentDateTime());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                takeId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return takeId;
    }

    @Override
    public void update(TakeExam TakeExam) {

    }

    @Override
    public void delete(TakeExam TakeExam) {

    }
}
