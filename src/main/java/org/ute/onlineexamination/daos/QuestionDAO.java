package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Question;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class QuestionDAO implements DAO<Question> {
    TeacherDAO teacherDAO ;
    public QuestionDAO(){
        teacherDAO = new TeacherDAO();
    }
    @Override
    public List<Question> getAll() {
        return null;
    }

    @Override
    public Optional<Question> get(int id) {
        return Optional.empty();
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
    public void update(Question course) {
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
    public void delete(Question course) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Course WHERE id=? ")) {
            preparedStatement.setInt(1, course.getId());
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
                     "WHERE t.id = ?\n")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Question question = new Question();
                question.setId(rs.getInt("id"));
                question.setContent(rs.getString("content"));
                question.setActive(rs.getBoolean("active"));
                question.setCourse_id(rs.getInt("course_id"));
                question.setDeleted_at(rs.getTimestamp("deleted_at"));
                questions.add(question);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return questions;
    }

}