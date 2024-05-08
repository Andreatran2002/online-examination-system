package org.ute.onlineexamination.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.chart.LineChart;

import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;

import org.ute.onlineexamination.components.CourseStatusBuilder;
import org.ute.onlineexamination.daos.StudentDAO;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.User;

import org.ute.onlineexamination.components.CourseCardBuilder;
import org.ute.onlineexamination.components.TestCardBuilder;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Examination;

import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.round;

public class StudentController implements Initializable {

    public GridPane testListContent;
    public Pagination myTestPagination;
    public GridPane allCourseListPane;

    public Label labelEmail;
    public Label labelFullname;
    public Label labelPhonenumber;
    StudentDAO studentDAO;
    Student student;
    User user;


    public Label dbMoreInfo;
    public Label dbWelcome;
    public Label dbOverallScore;
    public Label dbFinishedTest;
    public TabPane studentTabPane;
    public Label dbFinishedCourse;
    public Label dbTotalCourse;
    public AnchorPane dbPerfomancePane;
    public ScrollPane dbCourseStatusPane;
    public VBox dbListCourseStatus;
    public Tab testTab;
    public Tab dbTab;
    public Tab courseTab;
    public Tab settingTab;

    ObservableList<Examination> myExams;
    ObservableList<Course> myCourses;
    ObservableList<Course> allCourses;
    ExamDAO examDAO;
    CourseDAO courseDAO;
    Integer currentTestPage = 0 ;
    Integer testLeft = 0 ;
    Integer totalTestPage ;
    Integer overallScore = 0 ;
    Integer finishedCourse = 0 ;
    Integer finishedTest = 0 ;
    Integer totalCourse = 0 ;
    LineChart<Number,Number> dbPerformanceChart;
    XYChart.Series performanceSeries ;


    public StudentController(){
        studentDAO = new StudentDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO() ;
        performanceSeries = new XYChart.Series();
        dbListCourseStatus = new VBox();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Times");
        yAxis.setLabel("Score");
        dbPerformanceChart = new LineChart<Number,Number>(xAxis,yAxis);
        dbPerformanceChart.setTitle("Examination Performance");
        loadData();
        totalTestPage = (int) Math.ceil( myExams.size()/9);
    }

    void loadData(){
        getExams();
        getCourse();
        getHomePageData();
        addDataPane();
        displayHomePage();
        loadUser();
    }

    void getHomePageData(){
        testLeft = examDAO.getTestLeft(AppUtils.CURRENT_ROLE.id);
        overallScore = examDAO.getOverallScore(AppUtils.CURRENT_ROLE.id);
        finishedCourse = examDAO.getFinishedCourse(AppUtils.CURRENT_ROLE.id);
        finishedTest = examDAO.getFinishedTest(AppUtils.CURRENT_ROLE.id);
        totalCourse = examDAO.getTotalCourse(AppUtils.CURRENT_ROLE.id);
        ObservableList<Double> testPerformances = examDAO.getTestPerformance(AppUtils.CURRENT_ROLE.id);
        for (int i = 0; i < testPerformances.size(); i++) {
            performanceSeries.getData().add(new XYChart.Data(i,testPerformances.get(i)));
        }
        dbPerformanceChart.getData().setAll(performanceSeries);
        myCourses = courseDAO.getByStudentId(AppUtils.CURRENT_ROLE.id);

    }

    void displayHomePage(){
        dbWelcome.setText("Welcome "+AppUtils.CURRENT_USER.getFull_name());
        if (testLeft == 0){
            dbMoreInfo.setText("You done have any tests to complete. Enroll more course!!" );
        }
        else {
            dbMoreInfo.setText("You have "+ testLeft + " tests to complete. Finish it!" );
        }
        dbOverallScore.setText(String.valueOf(overallScore));
        dbFinishedCourse.setText(String.valueOf(finishedCourse));
        dbFinishedTest.setText(String.valueOf(finishedTest));
        dbTotalCourse.setText(String.valueOf(totalCourse));
        dbPerfomancePane.getChildren().add(dbPerformanceChart);
        for (int i = 0; i < myCourses.size(); i++) {

            dbListCourseStatus.getChildren().add(new CourseStatusBuilder(myCourses.get(i)).build());
        }
        dbCourseStatusPane.setContent(dbListCourseStatus);
    }


    void addDataPane(){
        for (int i = 0; i < myExams.size(); i++) {
            TestCardBuilder test = new TestCardBuilder(myExams.get(i), new Callback() {
                @Override
                public Object call(Object param) {
                    loadData();
                    return true;
                }
            });
            Parent content = test.build();
            testListContent.add(content , i % 3, round(i/3), 1,1);
        }



        for (int i = 0; i < allCourses.size(); i++) {
            CourseCardBuilder course = new CourseCardBuilder(allCourses.get(i), new Callback() {
                @Override
                public Object call(Object param) {
                    loadData();
                    return true ;
                }
            });
            Parent content = course.build();
            allCourseListPane.add(content , i % 3, round(i/3), 1,1);
        }
    }

    public void navToCoursePage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Online Examination System");
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("CoursesPage.fxml"));
        stage.setScene(new Scene(panel, 1000, 800));
        stage.show();
    }

    public void navToUpdateStudentPage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Update Student");
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("UpdateStudentPage.fxml"));
        stage.setScene(new Scene(panel, 400, 400));
        stage.show();
    }
    public void navToChangePasswordPage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Change Password");
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("ChangePasswordPage.fxml"));
        stage.setScene(new Scene(panel, 400, 400));
        stage.show();
    }
    public void loadUser(){
        user = AppUtils.CURRENT_USER;
        labelEmail.setText(user.getEmail());
        labelFullname.setText(user.getFull_name());
        labelPhonenumber.setText(user.getMobile());
    }

    void getCourse(){
        allCourses = courseDAO.getFilterAndPaging();
    }
    void getExams(){
        myExams = examDAO.getByStudentId(AppUtils.CURRENT_ROLE.id);
    }


    public void goToTestTab(ActionEvent event) {
        studentTabPane.getSelectionModel().select(testTab);
    }

    public void logout(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("LoginPage.fxml"));
        stage.setScene(new Scene(panel, 600, 400));
        stage.show();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();;
    }
}
