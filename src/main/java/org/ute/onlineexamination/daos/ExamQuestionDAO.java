package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.ExamQuestion;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ExamQuestionDAO implements DAO<ExamQuestion> {
    @Override
    public List<ExamQuestion> getAll() {
        return null;
    }

    @Override
    public Optional<ExamQuestion> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(ExamQuestion examQuestion) {
        int questionId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO ExamQuestion (exam_id, question_id , priority, created_at) VALUES ( ?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, examQuestion.getExam_id());
            preparedStatement.setInt(2, examQuestion.getQuestion_id());
            preparedStatement.setInt(3, examQuestion.getPriority());
            preparedStatement.setTimestamp(4, AppUtils.getCurrentDateTime());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                questionId = rs.getInt(1);
            }
            return questionId;

        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return questionId;
    }

    @Override
    public void update(ExamQuestion examQuestion) {

    }

    @Override
    public void delete(ExamQuestion examQuestion) {

    }
}
