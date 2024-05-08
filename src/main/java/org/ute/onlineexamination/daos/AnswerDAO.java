package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Answer;
import org.ute.onlineexamination.models.TeacherUser;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AnswerDAO implements DAO<Answer> {
    TeacherDAO teacherDAO ;
    public AnswerDAO(){
        teacherDAO = new TeacherDAO();
    }
    @Override
    public List<Answer> getAll() {
        return null;
    }

    @Override
    public Optional<Answer> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(Answer answer) {
        int answerId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Answer (question_id, content , correct, created_at) VALUES ( ?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, answer.getQuestion_id());
            preparedStatement.setString(2, answer.getContent());
            preparedStatement.setBoolean(3, answer.getCorrect());
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
    public void update(Answer answer) {
        try (Connection connection = DBConnectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Answer SET content=? , correct=? , updated_at=? WHERE id=? ")) {
            preparedStatement.setString(1, answer.getContent());
            preparedStatement.setBoolean(2, answer.getCorrect());
            preparedStatement.setTimestamp(3, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(4, answer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(Answer answer) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Answer SET deleted_at=? WHERE id=? ")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(2, answer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }
    public ObservableList<Answer> getByQuestionId(Integer id ){
        ObservableList<Answer> answers = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement( "select * from Answer where question_id = ? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Answer answer = new Answer();
                answer.setId(rs.getInt("id"));
                answer.setContent(rs.getString("content"));
                answer.setCorrect(rs.getBoolean("correct"));
                answer.setQuestion_id(rs.getInt("question_id"));
                answer.setDeleted_at(rs.getTimestamp("deleted_at"));
                answers.add(answer);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return answers;
    }

}
