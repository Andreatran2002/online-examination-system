package org.ute.onlineexamination.daos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.ute.onlineexamination.base.DAO;
import org.ute.onlineexamination.database.DBConnectionFactory;
import org.ute.onlineexamination.models.*;
import org.ute.onlineexamination.models.enums.PagingType;
import org.ute.onlineexamination.models.tablemodels.StudentCourseOverview;
import org.ute.onlineexamination.utils.AppUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAO implements DAO<Course> {
    TeacherDAO teacherDAO ;
    public CourseDAO(){
        teacherDAO = new TeacherDAO();
    }
    @Override
    public List<Course> getAll() {
        return null;
    }

    @Override
    public Optional<Course> get(int id) {
        Course course = new Course();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Course WHERE id=? ")) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                course.setId(rs.getInt("id"));
                course.setEnd(rs.getTimestamp("end"));
                course.setStart(rs.getTimestamp("start"));
                course.setCreated_at(rs.getTimestamp("created_at"));
                course.setCategory(rs.getString("category"));
                course.setDescription(rs.getString("description"));
                course.setName(rs.getString("name"));
                course.setTeacher_id(rs.getInt("teacher_id"));

            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return Optional.of(course);
    }

    @Override
    public Integer save(Course course) {
        Integer courseId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Course (teacher_id, name , description, start , end , created_at , category) VALUES ( ?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            Teacher teacher = teacherDAO.getByUserId(AppUtils.CURRENT_USER.getId());
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setString(2, course.getName());
            preparedStatement.setString(3, course.getDescription());
            preparedStatement.setTimestamp(4, course.getStart());
            preparedStatement.setTimestamp(5, course.getEnd());
            preparedStatement.setTimestamp(6, AppUtils.getCurrentDateTime());
            preparedStatement.setString(7, course.getCategory());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                courseId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return courseId;
    }

    @Override
    public void update(Course course) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Course SET name=? , description=? , start=? , end=?, category=?, updated_at=? WHERE id=? ")) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.setTimestamp(3, course.getStart());
            preparedStatement.setTimestamp(4, course.getEnd());
            preparedStatement.setString(5, course.getCategory());
            preparedStatement.setTimestamp(6, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(7, course.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    @Override
    public void delete(Course course) {
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Course SET deleted_at=? WHERE id=? ")) {
            preparedStatement.setTimestamp(1, AppUtils.getCurrentDateTime());
            preparedStatement.setInt(2, course.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
    }

    public ObservableList<Course> getByTeacherId(Integer id ){
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Course WHERE teacher_id=? AND deleted_at IS NULL ")) {
            preparedStatement.setInt(1, id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Course course = new Course();
                // TODO: lay thong tin User
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setDeleted_at(rs.getTimestamp("deleted_at"));
                course.setStart(rs.getTimestamp("start"));
                course.setEnd(rs.getTimestamp("end"));
                course.setCategory(rs.getString("category"));
                course.setTeacher_id(rs.getInt("teacher_id"));
                courses.add(course);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return courses;
    }
    public ObservableList<Course> getByStudentId(Integer student_id ){
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT c.* FROM CourseRegistration cr\n" +
                     "INNER JOIN Course c ON cr.course_id = c.id \n" +
                     "WHERE cr.student_id=? ")) {
            preparedStatement.setInt(1, student_id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setDeleted_at(rs.getTimestamp("deleted_at"));
                course.setStart(rs.getTimestamp("start"));
                course.setEnd(rs.getTimestamp("end"));
                course.setCategory(rs.getString("category"));
                course.setTeacher_id(rs.getInt("teacher_id"));
                courses.add(course);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return courses;
    }

    public Boolean checkEnroll(Integer course_id){
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM CourseRegistration WHERE course_id = ? AND student_id=? AND deleted_at IS NULL ")) {
            preparedStatement.setInt(1, course_id );
            preparedStatement.setInt(2, AppUtils.CURRENT_ROLE.id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                if (rs.getInt(1) > 0){
                    return true;
                }
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return false;
    }
    public Integer enrollCourse(Integer course_id){
        Integer registId = -1;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO CourseRegistration ( course_id,student_id, created_at) VALUES ( ?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, course_id );
            preparedStatement.setInt(2, AppUtils.CURRENT_ROLE.id );
            preparedStatement.setTimestamp(3, AppUtils.getCurrentDateTime() );
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                registId = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return registId;
    }

    public Integer getTotalStudent(Integer course_id) {
        Integer total = 0;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM `CourseRegistration` WHERE course_id = ? AND deleted_at IS NULL ")) {
            preparedStatement.setInt(1, course_id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return total;
    }

    public ObservableList<StudentCourseOverview> getStudentCourseOverview(Integer course_id) {
        ObservableList<StudentCourseOverview> data = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT u.full_name, u.mobile, u.email,COUNT(DISTINCT te.exam_id) AS number_of_exams_taken,cr.created_at AS registration_date\n" +
                     "FROM User u\n" +
                     "INNER JOIN Student s ON u.id = s.user_id\n" +
                     "INNER JOIN CourseRegistration cr ON s.id = cr.student_id\n" +
                     "LEFT JOIN TakeExam te ON s.id = te.student_id \n" +
                     "WHERE cr.deleted_at IS NULL AND cr.course_id = ? AND te.exam_id IN (SELECT id FROM Examination WHERE course_id = ?)\n" +
                     "GROUP BY u.full_name, u.mobile, u.email, cr.created_at;\n")) {
            preparedStatement.setInt(1, course_id );
            preparedStatement.setInt(2, course_id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                StudentCourseOverview student = new StudentCourseOverview();
                student.setFullName(rs.getString("full_name"));
                student.setEmail(rs.getString("email"));
                student.setMobile(rs.getString("mobile"));
                student.setTotalTestDone(rs.getInt("number_of_exams_taken"));
                student.setRegisterAt(rs.getTimestamp("registration_date"));
                data.add(student);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return data;
    }
    public  ObservableList<XYChart.Series> getPassChartData(Integer course_id){
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT  e.name,\n" +
                     "    (COUNT(CASE WHEN te.scoring > 5 THEN 1 END) / COUNT(*)) * 100 AS percentage\n" +
                     "FROM TakeExam te\n" +
                     "JOIN Examination e ON te.exam_id = e.id\n" +
                     "WHERE e.course_id = ? GROUP BY e.name;\n")) {
            preparedStatement.setInt(1, course_id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                XYChart.Series series = new XYChart.Series();
                series.setName(rs.getString("name"));
                series.getData().add(new XYChart.Data(rs.getString("name"), rs.getDouble("percentage")));
                data.add(series);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return data;
    }

    public  ObservableList<XYChart.Series> getScoreChartData(Integer course_id){
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT\n" +
                     "    c.id AS course_id,\n" +
                     "    u.email AS email,\n" +
                     "    AVG(\n" +
                     "        CASE \n" +
                     "            WHEN e.scoring_type = 1 THEN (SELECT MAX(scoring) FROM TakeExam WHERE exam_id = e.id)\n" +
                     "            WHEN e.scoring_type = 2 THEN (SELECT AVG(scoring) FROM TakeExam WHERE exam_id = e.id)\n" +
                     "            ELSE 0\n" +
                     "        END\n" +
                     "    ) AS average_score\n" +
                     "FROM Course c\n" +
                     "LEFT JOIN Examination e ON c.id = e.course_id\n" +
                     "LEFT JOIN TakeExam te ON e.id = te.exam_id\n" +
                     "LEFT JOIN Student s ON te.student_id = s.id\n" +
                     "LEFT JOIN User u ON s.user_id = u.id\n" +
                     "WHERE c.id = ? AND u.email IS NOT NULL\n" +
                     "GROUP BY c.id, u.email")) {
            preparedStatement.setInt(1, course_id );
            ResultSet rs = preparedStatement.executeQuery();
            Integer i = 1;
            while (rs.next()){
                XYChart.Series series = new XYChart.Series();
                series.setName(rs.getString(""));
                series.getData().add(new XYChart.Data(rs.getString("name"), rs.getDouble("percentage")));
                data.add(series);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return data;
    }

    public ObservableList<XYChart.Series> getPassChartDataByStudent(Integer course_id, Integer student_id) {
        ObservableList<XYChart.Series> data = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT  e.name,\n" +
                     "    (COUNT(CASE WHEN te.scoring > 5 THEN 1 END) / COUNT(*)) * 100 AS percentage\n" +
                     "FROM TakeExam te\n" +
                     "JOIN Examination e ON te.exam_id = e.id \n" +
                     "WHERE e.course_id = ? AND te.student_id=? GROUP BY e.name;\n")) {
            preparedStatement.setInt(1, course_id );
            preparedStatement.setInt(2, student_id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                XYChart.Series series = new XYChart.Series();
                series.setName(rs.getString("name"));
                series.getData().add(new XYChart.Data(rs.getString("name"), rs.getDouble("percentage")));
                data.add(series);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return data;
    }

    public Double getCourseFinalScore(Integer course_id , Integer student_id) {
        Double score = 0.0;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT \n" +
                     "    AVG(final_score) AS average_score\n" +
                     "FROM (SELECT\n" +
                     "        c.id AS course_id,\n" +
                     "        c.name,\n" +
                     "        CASE\n" +
                     "            WHEN e.scoring_type = 1 THEN MAX(te.scoring)   -- Lấy điểm cao nhất\n" +
                     "            WHEN e.scoring_type = 2 THEN AVG(te.scoring)   -- Lấy trung bình điểm\n" +
                     "            ELSE 0                                         -- Giá trị mặc định nếu không có loại điểm nào phù hợp\n" +
                     "        END AS final_score\n" +
                     "    FROM\n" +
                     "        Course c\n" +
                     "    LEFT JOIN\n" +
                     "        Examination e ON c.id = e.course_id\n" +
                     "    LEFT JOIN\n" +
                     "        TakeExam te ON e.id = te.exam_id\n" +
                     "    WHERE\n" +
                     "        c.id = ?\n" +
                     "        AND te.student_id = ?\n" +
                     "    GROUP BY\n" +
                     "        e.id ,e.scoring_type\n" +
                     ") AS subquery;\n")) {
            preparedStatement.setInt(1, course_id );
            preparedStatement.setInt(2, student_id );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                score = rs.getDouble("average_score");
            }
            return score;
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return score;
    }
    public ObservableList<Course> getPaging( Integer first_id, Integer last_id , Integer limit, PagingType type) {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Course WHERE id "+ (type == PagingType.AFTER && last_id!=0 ? "<":">" )+ " ? AND deleted_at IS NULL\n" +
                     "ORDER BY id DESC \n" +
                     "LIMIT ? ")) {
            preparedStatement.setInt(1, type == PagingType.AFTER ? last_id : first_id );
            preparedStatement.setInt(2, limit );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDescription(rs.getString("description"));
                course.setDeleted_at(rs.getTimestamp("deleted_at"));
                course.setStart(rs.getTimestamp("start"));
                course.setEnd(rs.getTimestamp("end"));
                course.setCategory(rs.getString("category"));
                course.setTeacher_id(rs.getInt("teacher_id"));
                courses.add(course);
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return courses;
    }

    public Boolean hasNext(Integer lastExam, Integer student_id) {
        Boolean result = false;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS (\n" +
                     "SELECT * FROM Course " +
                     "WHERE id < ? AND deleted_at IS NULL \n" +
                     "ORDER BY id DESC\n" +
                     ") AS has_next")) {
            preparedStatement.setInt(1, lastExam );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                result = rs.getInt(1) == 1 ;
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return result;
    }

    public Boolean hasBefore(Integer firstExam, Integer student_id) {
        Boolean result = false;
        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT EXISTS (\n" +
                     "SELECT * FROM Course \n" +
                     "WHERE id > ? AND deleted_at IS NULL \n" +
                     "ORDER BY id DESC\n" +
                     ") AS has_before")) {
            preparedStatement.setInt(1, firstExam );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                result = rs.getInt(1) == 1 ;
            }
        } catch (SQLException e) {
            DBConnectionFactory.printSQLException(e);
        }
        return result;
    }
}
