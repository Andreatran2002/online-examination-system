package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.ExamQuestion;
import org.ute.onlineexamination.models.Question;
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

    public ObservableList<ExamQuestion> getByExamId(Integer id ){
        ObservableList<ExamQuestion> questions = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ExamQuestion WHERE exam_id = ? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ExamQuestion question = new ExamQuestion();
                question.setId(rs.getInt("id"));
                question.setExam_id(rs.getInt("exam_id"));
                question.setQuestion_id(rs.getInt("question_id"));
                question.setPriority(rs.getInt("priority"));
                questions.add(question);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return questions;
    }
}
