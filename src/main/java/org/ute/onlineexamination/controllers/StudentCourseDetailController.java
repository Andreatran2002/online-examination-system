package org.ute.onlineexamination.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.daos.QuestionDAO;
import org.ute.onlineexamination.daos.TakeExamDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.TakeExam;
import org.ute.onlineexamination.tableviews.StudentTakeExamTableView;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class StudentCourseDetailController implements Initializable {
    public Label name;
    public Label category;
    public Label start;
    public Label end;
    public Label totalExams;
    public Label finalScore;
    public Label totalQuestions;
    public Label status;
    public AnchorPane passChartPane;
    public AnchorPane historyTakeExamPane;
    ExamDAO examDAO;
    QuestionDAO questionDAO;
    CourseDAO courseDAO;
    BarChart<String,Number> passChart;
    ObservableList<XYChart.Series> passSeries;

    Integer totalExam;
    Integer totalQuestion;
    Integer totalStudent;
    Double score;
    ObservableList<TakeExam> takeExams;

    TakeExamDAO takeExamDAO;

    Course course;

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
        takeExamDAO = new TakeExamDAO();

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
        takeExams = takeExamDAO.getByStudentId(AppUtils.CURRENT_ROLE.id,course.getId());
        passSeries = courseDAO.getPassChartDataByStudent(course.getId(), AppUtils.CURRENT_ROLE.id);
        score = courseDAO.getCourseFinalScore(course.getId(), AppUtils.CURRENT_ROLE.id);

    }

    void loadView(){
        name.setText(course.getName());
        category.setText(course.getCategory());
        start.setText(AppUtils.formatTime(course.getStart()));
        end.setText(AppUtils.formatTime(course.getEnd()));
        status.setText(getStatus(course.getStart(),course.getEnd()));
        totalQuestions.setText(String.valueOf(totalQuestion));
        totalExams.setText(String.valueOf(totalExam));
        finalScore.setText(String.valueOf(score));

        for (int i = 0; i < passSeries.size(); i++) {
            passChart.getData().add(passSeries.get(i));
        }
        passChartPane.getChildren().add(passChart);

        TableView examListTableView = new StudentTakeExamTableView(takeExams);
        examListTableView.prefHeightProperty().bind(historyTakeExamPane.heightProperty());
        examListTableView.prefWidthProperty().bind(historyTakeExamPane.widthProperty());
        historyTakeExamPane.getChildren().add(examListTableView);
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
