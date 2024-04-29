package org.ute.onlineexamination.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.chart.LineChart;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;

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
    public GridPane mycourseListPane;

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
    public LineChart dbPerformanceChart;
    public ListView dbListCourseStatus;
    public Tab testTab;
    public Tab dbTab;
    public Tab courseTab;
    public Tab settingTab;

    ObservableList<Examination> myExams;
    ObservableList<Course> myCourses;
    ExamDAO examDAO;
    CourseDAO courseDAO;
    Integer currentTestPage = 0 ;
    Integer testLeft = 0 ;
    Integer totalTestPage ;
    Integer overallScore = 0 ;
    Integer finishedCourse = 0 ;
    Integer finishedTest = 0 ;
    Integer totalCourse = 0 ;


    public StudentController(){
        studentDAO = new StudentDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO() ;
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



        for (int i = 0; i < myCourses.size(); i++) {
            CourseCardBuilder course = new CourseCardBuilder(myCourses.get(i), new Callback() {
                @Override
                public Object call(Object param) {
                    loadData();
                    return true ;
                }
            });
            Parent content = course.build();
            mycourseListPane.add(content , i % 3, round(i/3), 1,1);
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
        myCourses = courseDAO.getFilterAndPaging();
    }
    void getExams(){
        myExams = examDAO.getByStudentId(AppUtils.CURRENT_ROLE.id);
    }


    public void goToTestTab(ActionEvent event) {
        studentTabPane.getSelectionModel().select(testTab);
    }

}
