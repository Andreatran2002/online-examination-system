package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.*;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class QuestionDAO implements DAO<Question> {
    TeacherDAO teacherDAO ;
    AnswerDAO answerDAO;
    public QuestionDAO(){
        teacherDAO = new TeacherDAO();
        answerDAO = new AnswerDAO();
    }
    @Override
    public List<Question> getAll() {
        return null;
    }

    @Override
    public Optional<Question> get(int id) {
        Question question = new Question();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Question WHERE id=? AND deleted_at IS NULL AND active=1")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                question.setId(rs.getInt("id"));
                question.setActive(rs.getBoolean("active"));
                question.setCourse_id(rs.getInt("course_id"));
                question.setContent(rs.getString("content"));
                ObservableList<Answer> answers = answerDAO.getByQuestionId(id);
                question.setAnswers(answers);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return Optional.of(question);
    }

    @Override
    public Integer save(Question question) {
        int questionId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Question (course_id, content , active, created_at) VALUES ( ?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, question.getCourse_id());
            preparedStatement.setString(2, question.getContent());
            preparedStatement.setBoolean(3, question.getActive());
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
    public void update(Question question) {
        try (Connection connection = DBConnectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Question SET content=? , active=?, course_id =?, updated_at=? WHERE id=? ")) {
            preparedStatement.setString(1, question.getContent());
            preparedStatement.setBoolean(2, question.getActive());
            preparedStatement.setInt(3, question.getCourse_id());
            preparedStatement.setTimestamp(4, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(5, question.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(Question question) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Question SET deleted_at=? WHERE id=? ")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(2, question.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }
    public ObservableList<Question> getByTeacherId(Integer id ){
        ObservableList<Question> questions = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.*\n" +
                     "FROM Question q\n" +
                     "INNER JOIN Course c ON q.course_id = c.id\n" +
                     "INNER JOIN Teacher t ON c.teacher_id = t.id\n" +
                     "WHERE t.id = ? AND q.deleted_at IS NULL \n")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setContent(rs.getString("content"));
                question.setActive(rs.getBoolean("active"));
                question.setCourse_id(rs.getInt("course_id"));
                question.setDeleted_at(rs.getTimestamp("deleted_at"));
                question.setAnswers( answerDAO.getByQuestionId(question.getId()));
                questions.add(question);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return questions;
    }
    public ObservableList<Question> getByCourseId(Integer id ){
        ObservableList<Question> questions = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Question WHERE course_id = ? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setContent(rs.getString("content"));
                question.setActive(rs.getBoolean("active"));
                question.setCourse_id(rs.getInt("course_id"));
                question.setDeleted_at(rs.getTimestamp("deleted_at"));
                question.setAnswers( answerDAO.getByQuestionId(question.getId()));
                questions.add(question);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return questions;
    }


    public ObservableList<Question> getByExamId(Integer id) {
        ObservableList<Question> questions = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.*, eq.priority FROM ExamQuestion eq\n" +
                     "INNER JOIN Question q ON q.id = eq.question_id \n" +
                     "WHERE eq.exam_id =? AND eq.deleted_at IS NULL")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setContent(rs.getString("content"));
                question.setActive(rs.getBoolean("active"));
                question.setCourse_id(rs.getInt("course_id"));
                question.setDeleted_at(rs.getTimestamp("deleted_at"));
                question.setPriority(rs.getInt("priority"));
                question.setAnswers( answerDAO.getByQuestionId(question.getId()));
                questions.add(question);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return questions;
    }
}
