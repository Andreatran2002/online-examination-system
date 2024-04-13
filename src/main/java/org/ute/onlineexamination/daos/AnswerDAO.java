package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Answer;
import org.ute.onlineexamination.models.Question;
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
//        try (Connection connection = DBConnectionFactory.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Course SET name=? , description=? , start=? , end=?, category=?, updated_at=? WHERE id=? ")) {
//            preparedStatement.setString(1, course.getName());
//            preparedStatement.setString(2, course.getDescription());
//            preparedStatement.setTimestamp(3, course.getStart());
//            preparedStatement.setTimestamp(4, course.getEnd());
//            preparedStatement.setString(5, course.getCategory());
//            preparedStatement.setTimestamp(6, AppUtils.getCurrentDateTime());
//            preparedStatement.setInt(7, course.getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            DBConnectionFactory.printSQLException(e);
//        }
    }

    @Override
    public void delete(Answer course) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Course WHERE id=? ")) {
            preparedStatement.setInt(1, course.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }
    public ObservableList<Answer> getByCourseId(Integer id ){
        ObservableList<Answer> answers = FXCollections.observableArrayList();
//        try (Connection connection = DBConnectionFactory.getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.*\n" +
//                     "FROM Question q\n" +
//                     "INNER JOIN Course c ON q.course_id = c.id\n" +
//                     "INNER JOIN Teacher t ON c.teacher_id = t.id\n" +
//                     "WHERE t.id = ?\n")) {
//            preparedStatement.setInt(1, id );
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()){
//                Question question = new Question();
//                question.setId(rs.getInt("id"));
//                question.setContent(rs.getString("content"));
//                question.setActive(rs.getBoolean("active"));
//                question.setCourse_id(rs.getInt("course_id"));
//                question.setDeleted_at(rs.getTimestamp("deleted_at"));
//                questions.add(question);
//            }
//        } catch (SQLException e) {
//            DBConnectionFactory.printSQLException(e);
//        }
        return answers;
    }

}
