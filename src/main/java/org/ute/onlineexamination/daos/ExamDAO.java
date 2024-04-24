package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamDAO implements DAO<Examination> {

    CourseDAO courseDAO;
    public ExamDAO(){
        courseDAO = new CourseDAO();
    }

    @Override
    public List<Examination> getAll() {
        return null;
    }

    @Override
    public Optional<Examination> get(int id) {
        return Optional.empty();
    }

    @Override
    public Integer save(Examination examination) {
        Integer examId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Examination (course_id, name ,description, start, end , times_retry, scoring_type, created_at) VALUES ( ?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, examination.getCourse_id());
            preparedStatement.setString(2, examination.getName());
            preparedStatement.setString(3, examination.getDescription());
            preparedStatement.setTimestamp(4, examination.getStart());
            preparedStatement.setTimestamp(5, examination.getEnd());
            preparedStatement.setInt(6, examination.getTime_retry());
            preparedStatement.setInt(7, examination.getScoring_type());
            preparedStatement.setTimestamp(8, AppUtils.getCurrentDateTime());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                examId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return examId;
    }

    @Override
    public void update(Examination examination) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Examination SET name=? , description=? , start=? , end=?, course_id=?, times_retry=? , scoring_type=? , updated_at=? WHERE id=? ")) {
            preparedStatement.setString(1, examination.getName());
            preparedStatement.setString(2, examination.getDescription());
            preparedStatement.setTimestamp(3, examination.getStart());
            preparedStatement.setTimestamp(4, examination.getEnd());
            preparedStatement.setInt(5, examination.getCourse_id());
            preparedStatement.setInt(6, examination.getTime_retry());
            preparedStatement.setInt(7, examination.getScoring_type());
            preparedStatement.setTimestamp(8, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(9, examination.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(Examination examination) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Examination SET deleted_at=? WHERE id=? ")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(1, examination.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    public ObservableList<Examination> getByTeacherId(Integer id ){
        ObservableList<Examination> examinations = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.*\n" +
                     "FROM Examination q\n" +
                     "INNER JOIN Course c ON q.course_id = c.id\n" +
                     "INNER JOIN Teacher t ON c.teacher_id = t.id\n" +
                     "WHERE t.id = ? AND q.deleted_at IS NULL \n")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Examination exam = new Examination();
                exam.setId(rs.getInt("id"));
                exam.setName(rs.getString("name"));
                exam.setCourse_id(rs.getInt("course_id"));
                exam.setDescription(rs.getString("description"));
                exam.setStart(rs.getTimestamp("start"));
                exam.setEnd(rs.getTimestamp("end"));
                exam.setTime_retry(rs.getInt("times_retry"));
                exam.setTime_retry(rs.getInt("scoring_type"));
                Optional<Course> course = courseDAO.get(rs.getInt("course_id"));
                exam.course = course.get();
                examinations.add(exam);

            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return examinations;
    }
}
