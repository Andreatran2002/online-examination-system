package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.ExamQuestion;
import org.ute.onlineexamination.models.Examination;
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
        TakeExam takeExam = new TakeExam();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TakeExam WHERE id=? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                takeExam.setId(rs.getInt("id"));
                takeExam.setEnd(rs.getTimestamp("end"));
                takeExam.setStart(rs.getTimestamp("start"));
                takeExam.setStudent_id(rs.getInt("student_id"));
                takeExam.setScoring(rs.getDouble("scoring"));
                takeExam.setExam_id(rs.getInt("exam_id"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return Optional.of(takeExam);
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
    public void update(TakeExam takeExam) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE TakeExam SET scoring = ?, end = ? , updated_at = ? WHERE id=? ")) {
            preparedStatement.setDouble(1, takeExam.getScoring());
            preparedStatement.setTimestamp(2, AppUtils.getCurrentDateTime());
            preparedStatement.setTimestamp(3, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(4, takeExam.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(TakeExam takeExam) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE TakeExam SET deleted_at = ? WHERE id=? ")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(2, takeExam.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    public ObservableList<Double> getScoreByExamId(Integer exam_id) {
        ObservableList<Double> scores = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT scoring FROM TakeExam WHERE exam_id=? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, exam_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                scores.add(rs.getDouble("scoring"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return scores;
    }

    public Double getPassPercentageByExamId(Integer exam_id){
        Double percentage = 50.0;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT (COUNT(CASE WHEN scoring > 5.0 THEN 1 END) / COUNT(*)) * 100 AS percentage FROM TakeExam WHERE exam_id = ? AND deleted_at IS NULL ")) {
            preparedStatement.setInt(1, exam_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                percentage = rs.getDouble("percentage");
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return percentage;
    }
}
