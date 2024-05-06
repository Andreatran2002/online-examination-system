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


public class ExamDAO implements DAO<Examination> {

    CourseDAO courseDAO;
    ExamQuestionDAO examQuestionDAO;
    public ExamDAO(){
        courseDAO = new CourseDAO();
        examQuestionDAO = new ExamQuestionDAO();
    }

    @Override
    public List<StudentUser> getAll() {
        return null;
    }

    @Override
    public Optional<Examination> get(int id) {
        Examination exam = new Examination();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Examination WHERE id=? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                exam.setId(rs.getInt("id"));
                exam.setEnd(rs.getTimestamp("end"));
                exam.setStart(rs.getTimestamp("start"));
                exam.setCourse_id(rs.getInt("course_id"));
                exam.setDescription(rs.getString("description"));
                exam.setName(rs.getString("name"));
                exam.setTime_retry(rs.getInt("times_retry"));
                exam.setTotal_minutes(rs.getInt("total_minutes"));
                exam.setScoring_type(rs.getInt("scoring_type"));
                ObservableList<ExamQuestion> questions = examQuestionDAO.getByExamId(exam.getId());
                exam.questions = questions;
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return Optional.of(exam);
    }

    @Override
    public Integer save(Examination examination) {
        Integer examId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Examination (course_id, name ,description, start, end , times_retry, scoring_type, total_minutes, created_at) VALUES ( ?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, examination.getCourse_id());
            preparedStatement.setString(2, examination.getName());
            preparedStatement.setString(3, examination.getDescription());
            preparedStatement.setTimestamp(4, examination.getStart());
            preparedStatement.setTimestamp(5, examination.getEnd());
            preparedStatement.setInt(6, examination.getTime_retry());
            preparedStatement.setInt(7, examination.getScoring_type());
            preparedStatement.setInt(8, examination.getTotal_minutes());
            preparedStatement.setTimestamp(9, AppUtils.getCurrentDateTime());
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
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Examination SET name=? , description=? , start=? , end=?, course_id=?, times_retry=? , scoring_type=? , total_minutes=? , updated_at=? WHERE id=? ")) {
            preparedStatement.setString(1, examination.getName());
            preparedStatement.setString(2, examination.getDescription());
            preparedStatement.setTimestamp(3, examination.getStart());
            preparedStatement.setTimestamp(4, examination.getEnd());
            preparedStatement.setInt(5, examination.getCourse_id());
            preparedStatement.setInt(6, examination.getTime_retry());
            preparedStatement.setInt(7, examination.getScoring_type());
            preparedStatement.setInt(8, examination.getTotal_minutes());
            preparedStatement.setTimestamp(9, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(10, examination.getId());
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
                exam.setScoring_type(rs.getInt("scoring_type"));
                exam.setTotal_minutes(rs.getInt("total_minutes"));
                Optional<Course> course = courseDAO.get(rs.getInt("course_id"));
                exam.course = course.get();
                examinations.add(exam);

            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return examinations;
    }
    public ObservableList<Examination> getByStudentId(Integer id ){
        ObservableList<Examination> examinations = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT q.*\n" +
                     "FROM Examination q\n" +
                     "INNER JOIN Course c ON q.course_id = c.id\n" +
                     "INNER JOIN CourseRegistration cr ON cr.course_id = c.id\n" +
                     "INNER JOIN Student t ON cr.student_id = t.id\n" +
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
                exam.setScoring_type(rs.getInt("scoring_type"));
                exam.setTotal_minutes(rs.getInt("total_minutes"));
                Optional<Course> course = courseDAO.get(rs.getInt("course_id"));
                ObservableList<ExamQuestion> questions = examQuestionDAO.getByExamId(rs.getInt("id"));
                exam.course = course.get();
                exam.questions = questions;
                examinations.add(exam);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return examinations;
    }

    public Double mark(Integer take_exam_id) throws SQLException {
         Double score = 0.0;
         Integer totalPriority = 0 ;
         Connection connection = DBConnectionFactory.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement("SELECT eq.id , eq.question_id, eq.priority  FROM `TakeExam` te \n" +
                     "\tINNER JOIN Examination e ON e.id = te.exam_id \n" +
                     "    INNER JOIN ExamQuestion eq ON eq.exam_id = e.id\n" +
                     "    WHERE te.id = ? ");
        preparedStatement.setInt(1, take_exam_id );
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<Double> results = FXCollections.observableArrayList();
        while (rs.next()){
            Integer totalCorrect = 0 ;
            Integer correctCount = 0 ;
            Integer questionId = rs.getInt("question_id");
            Integer examQuestionId = rs.getInt("id");
            Integer priority = rs.getInt("priority");
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM `Answer` WHERE question_id = ? AND correct=1");
            preparedStatement.setInt(1,questionId);
            ResultSet countRs = preparedStatement.executeQuery();
            while (countRs.next()){
                totalCorrect = countRs.getInt(1);
            }
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM `TakeExamAnswer` t \n" +
                    "INNER JOIN Answer a ON a.id = t.answer_id AND a.correct = 1\n" +
                    "WHERE t.exam_question_id = ? AND t.take_exam_id = ?");
            preparedStatement.setInt(1,examQuestionId);
            preparedStatement.setInt(2,take_exam_id);
            countRs = preparedStatement.executeQuery();
            while (countRs.next()){
                correctCount = countRs.getInt(1);
            }

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM `TakeExamAnswer` t INNER JOIN Answer a ON a.id = t.answer_id WHERE a.correct = 0 AND t.exam_question_id = ? AND t.take_exam_id = ?");
            preparedStatement.setInt(1,examQuestionId);
            preparedStatement.setInt(2,take_exam_id);
            countRs = preparedStatement.executeQuery();
            while (countRs.next()){
                if (correctCount >= countRs.getInt(1)){
                    correctCount = correctCount - countRs.getInt(1);
                }
                else correctCount = 0 ;
            }
            totalPriority = totalPriority + priority;
            results.add((double) (correctCount*priority/totalCorrect));
        }

        for (int i = 0; i < results.size(); i++) {
            score = score + (results.get(i))/(totalPriority) ;
        }

        return AppUtils.round(score*10,2);
    }
    public  Integer checkTakeExamTimes ( Integer exam_id){
        Integer times = 0 ;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM TakeExam WHERE exam_id = ? AND deleted_at IS NULL")) {
            preparedStatement.setInt(1, exam_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                times = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return times;
    }

    public Integer getTestLeft(Integer student_id){
        Integer lefts = 0 ;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Examination e INNER JOIN CourseRegistration cr ON e.course_id = cr.course_id WHERE cr.student_id = ? AND e.id NOT IN ( SELECT exam_id FROM TakeExam );")) {
            preparedStatement.setInt(1, student_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                lefts = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return lefts;
    }

    public Integer getOverallScore(Integer student_id) {
        Integer score = 0 ;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT AVG(scoring) FROM `TakeExam` WHERE student_id=?")) {
            preparedStatement.setInt(1, student_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                score = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return score;
    }

    public Integer getFinishedCourse(Integer student_id) {
        Integer totals = 0 ;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM CourseRegistration cr \n" +
                     "INNER JOIN Course c ON c.id = cr.course_id \n" +
                     "WHERE c.end < CURRENT_DATE() AND cr.student_id = ?")) {
            preparedStatement.setInt(1, student_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                totals = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return totals;
    }

    public Integer getFinishedTest(Integer student_id) {
        Integer totals = 0 ;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(DISTINCT exam_id) \n" +
                     "FROM TakeExam \n" +
                     "WHERE student_id = ?")) {
            preparedStatement.setInt(1, student_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                totals = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return totals;
    }

    public Integer getTotalCourse(Integer student_id) {
        Integer totals = 0 ;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CourseRegistration WHERE student_id=?")) {
            preparedStatement.setInt(1, student_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                totals = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return totals;
    }
}
