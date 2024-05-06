package org.ute.onlineexamination.daos;

import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.StudentUser;
import org.ute.onlineexamination.models.TakeExamAnswer;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class TakeExamAnswerDAO implements DAO<TakeExamAnswer> {
    @Override
    public List<StudentUser> getAll() {
        return null;
    }

    @Override
    public Optional<TakeExamAnswer> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(TakeExamAnswer takeExamAnswer) {
        Integer answerId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TakeExamAnswer (take_exam_id, exam_question_id, answer_id, created_at) VALUES ( ?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1,takeExamAnswer.getTake_exam_id());
            preparedStatement.setInt(2, takeExamAnswer.getExam_question_id());
            preparedStatement.setInt(3, takeExamAnswer.getAnswer_id());
            preparedStatement.setTimestamp(4, AppUtils.getCurrentDateTime());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                answerId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return answerId;
    }

    @Override
    public void update(TakeExamAnswer takeExamAnswer) {

    }

    @Override
    public void delete(TakeExamAnswer takeExamAnswer) {

    }
}
