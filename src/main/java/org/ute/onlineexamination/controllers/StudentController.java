package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.text.Text;
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

import org.ute.onlineexamination.models.enums.PagingType;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.round;

public class StudentController implements Initializable {

    public GridPane testListContent;
    public GridPane allCourseListPane;
    public Label labelEmail;
    public Label labelFullname;
    public Label labelPhonenumber;
    @FXML
    public Button testBackBtn;
    @FXML
    public Button testNextBtn;
    @FXML
    public Button courseBackBtn;
    @FXML
    public Button courseNextBtn;

    Boolean testHasNext ;
    Boolean testHasBefore;
    Boolean courseHasNext ;
    Boolean courseHasBefore;
    StudentDAO studentDAO;
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
    Integer testLeft = 0 ;
    Integer overallScore = 0 ;
    Integer finishedCourse = 0 ;
    Integer finishedTest = 0 ;
    Integer totalCourse = 0 ;
    LineChart<Number,Number> dbPerformanceChart;
    XYChart.Series performanceSeries ;

    Integer lastExam = 0 ;
    Integer firstExam = 0 ;
    Integer lastCourse = 0 ;
    Integer firstCourse = 0 ;


    public StudentController(){
        studentDAO = new StudentDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO() ;
        testHasNext = false;
        testHasBefore = false;
        courseHasBefore = false;
        courseHasNext = false;
        loadView();
        loadData();
    }

    void loadView(){
        performanceSeries = new XYChart.Series();
        dbListCourseStatus = new VBox();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        dbPerformanceChart = new LineChart<Number,Number>(xAxis,yAxis);
        dbPerformanceChart.setTitle("Examination Performance");
        xAxis.setLabel("Times");
        yAxis.setLabel("Score");

        testNextBtn.setDisable(testHasNext);
        testBackBtn.setDisable(testHasBefore);
        testCards = FXCollections.observableArrayList();
        courseCards = FXCollections.observableArrayList();

    }


    void loadData(){
        firstExam = 0 ;
        lastExam = 0 ;
        firstCourse = 0 ;
        lastCourse = 0 ;
        getExams(PagingType.AFTER);
        getCourses(PagingType.AFTER);
        getHomePageData();
        getExamDataPane();
        getCourseDataPane();
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
        dbPerfomancePane.getChildren().setAll(dbPerformanceChart);
        dbListCourseStatus = new VBox();
        for (int i = 0; i < myCourses.size(); i++) {
            dbListCourseStatus.getChildren().add(new CourseStatusBuilder(myCourses.get(i)).build());
        }
        dbCourseStatusPane.setContent(dbListCourseStatus);
    }

    ObservableList<Parent> testCards ;
    ObservableList<Parent> courseCards ;
    void getExamDataPane(){
        testListContent.getChildren().removeAll(testCards);
        testCards = FXCollections.observableArrayList();
        for (int i = 0; i < 9; i++) {
            if ( i < myExams.size() ) {
                TestCardBuilder test = new TestCardBuilder(myExams.get(i), new Callback() {
                    @Override
                    public Object call(Object param) {
                        loadData();
                        return true;
                    }
                });
                Parent content = test.build();
                testCards.add(content);
                testListContent.add(content, i % 3, round(i / 3), 1, 1);
            }}
    }

    void getCourseDataPane(){
        allCourseListPane.getChildren().removeAll(courseCards);
        courseCards = FXCollections.observableArrayList();
        for (int i = 0; i < 9; i++) {
            if ( i < allCourses.size() ) {
                CourseCardBuilder course = new CourseCardBuilder(allCourses.get(i), new Callback() {
                    @Override
                    public Object call(Object param) {
                        loadData();
                        return true ;
                    }
                });
                Parent content = course.build();
                courseCards.add(content);
                allCourseListPane.add(content, i % 3, round(i / 3), 1, 1);
            }
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

    void getCourses(PagingType type){
        allCourses = courseDAO.getPaging(firstCourse, lastCourse,9, type );
        if (allCourses.size()>0){
            lastCourse = allCourses.getLast().getId();
            firstCourse = allCourses.getFirst().getId();
            courseHasNext = courseDAO.hasNext(lastCourse,AppUtils.CURRENT_ROLE.id);
            courseHasBefore = courseDAO.hasBefore(firstCourse,AppUtils.CURRENT_ROLE.id);
            courseNextBtn.setDisable(!courseHasNext);
            courseBackBtn.setDisable(!courseHasBefore);
        }
    }
    void getExams(PagingType type){
        myExams = examDAO.getPagingByStudentId(AppUtils.CURRENT_ROLE.id, firstExam, lastExam,9, type );
        if (myExams.size()>0){
            lastExam = myExams.getLast().getId();
            firstExam = myExams.getFirst().getId();
            testHasNext = examDAO.hasNext(lastExam,AppUtils.CURRENT_ROLE.id);
            testHasBefore = examDAO.hasBefore(firstExam,AppUtils.CURRENT_ROLE.id);
            testNextBtn.setDisable(!testHasNext);
            testBackBtn.setDisable(!testHasBefore);
        }
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

    public void onTestBack(ActionEvent actionEvent) {
        getExams(PagingType.BEFORE);
        getExamDataPane();
    }

    public void onTestNext(ActionEvent actionEvent) {
        getExams(PagingType.AFTER);
        getExamDataPane();
    }

    public void onCourseNext(ActionEvent actionEvent) {
        getCourses(PagingType.AFTER);
        getCourseDataPane();
    }

    public void onCourseBack(ActionEvent actionEvent) {
        getCourses(PagingType.BEFORE);
        getCourseDataPane();
    }
}
