package org.ute.onlineexamination.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.daos.QuestionDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.tablemodels.StudentCourseOverview;
import org.ute.onlineexamination.tableviews.ExaminationTableView;
import org.ute.onlineexamination.tableviews.StudentCourseOverviewTableView;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ResourceBundle;

public class DetailCourseController implements Initializable {
    public Label totalStudents;
    public Label totalQuestions;
    public Label status;
    public Label name;
    public Label category;
    public Label start;
    public Label end;
    public Label totalExams;
    public AnchorPane passChartPane;
    public AnchorPane examListPane;
    public AnchorPane studentOverviewPane;
    TableView<StudentCourseOverview> studentTableView;
    Integer totalExam;
    Integer totalQuestion;
    Integer totalStudent;

    ExamDAO examDAO;
    QuestionDAO questionDAO;
    CourseDAO courseDAO;
    BarChart<String,Number> passChart;
    ObservableList<Examination> examinations;
    ObservableList<XYChart.Series> passSeries;


    Course course;
    ObservableList<StudentCourseOverview> studentCourseOverviews;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseDAO = new CourseDAO();
        examDAO = new ExamDAO();
        questionDAO = new QuestionDAO();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        passChart = new BarChart<String,Number>(xAxis,yAxis);
        passChart.setTitle("Pass percentage per examination");
        xAxis.setLabel("Examination");
        yAxis.setLabel("Percentage");

        loadData();
        loadView();
    }
    void loadData(){
        totalExam = examDAO.getTotalByCourse(course.getId());
        totalQuestion = questionDAO.getByCourseId(course.getId()).size();
        totalStudent = courseDAO.getTotalStudent(course.getId());
        studentCourseOverviews = courseDAO.getStudentCourseOverview(course.getId());
        passSeries = courseDAO.getPassChartData(course.getId());
        examinations= examDAO.getByCourseId(course.getId());
    }

    void loadView(){
        name.setText(course.getName());
        category.setText(course.getCategory());
        start.setText(AppUtils.formatTime(course.getStart()));
        end.setText(AppUtils.formatTime(course.getEnd()));
        status.setText(getStatus(course.getStart(),course.getEnd()));
        totalStudents.setText(String.valueOf(totalStudent));
        totalQuestions.setText(String.valueOf(totalQuestion));
        totalExams.setText(String.valueOf(totalExam));
        studentTableView = new StudentCourseOverviewTableView(studentCourseOverviews);
        studentTableView.prefHeightProperty().bind(studentOverviewPane.heightProperty());
        studentTableView.prefWidthProperty().bind(studentOverviewPane.widthProperty());
        studentOverviewPane.getChildren().add(studentTableView);

        for (int i = 0; i < passSeries.size(); i++) {
            passChart.getData().add(passSeries.get(i));
        }
        passChartPane.getChildren().add(passChart);
        TableView examListTableView = new ExaminationTableView(examinations);
        examListTableView.prefHeightProperty().bind(examListPane.heightProperty());
        examListTableView.prefWidthProperty().bind(examListPane.widthProperty());
        examListPane.getChildren().add(examListTableView);

    }
    String getStatus(Timestamp startDayTimestamp, Timestamp endDayTimestamp){
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (currentTimestamp.getTime() < startDayTimestamp.getTime()) {
            return "Not started";
        } else if (currentTimestamp.getTime() >= startDayTimestamp.getTime() && currentTimestamp.getTime() <= endDayTimestamp.getTime()) {
            return "Started";
        } else {
            return "Finished";
        }
    }


}
