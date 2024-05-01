package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Answer;
import org.ute.onlineexamination.models.ExamQuestion;
import org.ute.onlineexamination.models.Question;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ExamQuestionDAO implements DAO<ExamQuestion> {
    QuestionDAO questionDAO;
    public ExamQuestionDAO(){
        questionDAO = new QuestionDAO();
    }
    @Override
    public List<ExamQuestion> getAll() {
        return null;
    }

    @Override
    public Optional<ExamQuestion> get(int id) {
        ExamQuestion question = new ExamQuestion();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ExamQuestion WHERE id=? AND deleted_at IS NULL AND active=1")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                question.setId(rs.getInt("id"));
                question.setPriority(rs.getInt("priority"));
                question.setExam_id(rs.getInt("exam_id"));
                question.setQuestion_id(rs.getInt("question_id"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return Optional.of(question);
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
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ExamQuestion SET priority=? , updated_at=? WHERE id=? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, examQuestion.getPriority());
            preparedStatement.setTimestamp(2, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(3, examQuestion.getId());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(ExamQuestion examQuestion) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ExamQuestion SET deleted_at=? WHERE id=? ")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(2, examQuestion.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
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
                question.question = questionDAO.get(rs.getInt("question_id")).get();
                questions.add(question);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return questions;
    }

    public void saveOrUpdateQuestion(Question question, Integer exam_id) {
        ExamQuestion examQuestion = new ExamQuestion(question.getId(), exam_id);
        examQuestion.setPriority(question.getPriority());
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("Select * from ExamQuestion WHERE question_id = ? AND exam_id=? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, question.getId());
            preparedStatement.setInt(2, exam_id);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                examQuestion.setId(rs.getInt("id"));
                update(examQuestion);
                return;
            }
            save(examQuestion);
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    public Optional<ExamQuestion> getByQuestionId(int question_id, int exam_id) {
        ExamQuestion question = new ExamQuestion();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ExamQuestion WHERE question_id=? AND exam_id=? AND deleted_at IS NULL ")) {
            preparedStatement.setInt(1, question_id);
            preparedStatement.setInt(2, exam_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                question.setId(rs.getInt("id"));
                question.setPriority(rs.getInt("priority"));
                question.setExam_id(rs.getInt("exam_id"));
                question.setQuestion_id(rs.getInt("question_id"));
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return Optional.of(question);
    }
}
