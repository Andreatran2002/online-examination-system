package org.ute.onlineexamination.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
<<<<<<< HEAD
import org.ute.onlineexamination.daos.StudentDAO;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.User;
=======
import org.ute.onlineexamination.components.CourseCardBuilder;
import org.ute.onlineexamination.components.TestCardBuilder;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Examination;
>>>>>>> 5cb2844c91b4a0c21cd7b7ecbf9d8a5ddf567bda
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.round;

public class StudentController implements Initializable {

    public GridPane testListContent;
    public Pagination myTestPagination;
    public GridPane mycourseListPane;
<<<<<<< HEAD
    public Label labelEmail;
    public Label labelFullname;
    public Label labelPhonenumber;
    StudentDAO studentDAO;
    Student student;
    User user;

=======
    ObservableList<Examination> myExams;
    ObservableList<Course> myCourses;
    ExamDAO examDAO;
    CourseDAO courseDAO;
    Integer currentTestPage = 0 ;
    Integer totalTestPage ;
>>>>>>> 5cb2844c91b4a0c21cd7b7ecbf9d8a5ddf567bda

    public StudentController(){
        studentDAO = new StudentDAO();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO() ;
        getExams();
        getCourse();
        addDataPane();
        totalTestPage = (int) Math.ceil( myExams.size()/9);
    }

    void addDataPane(){
        for (int i = 0; i < myExams.size(); i++) {
            TestCardBuilder test = new TestCardBuilder(myExams.get(i));
            Parent content = test.build();
            testListContent.add(content , i % 3, round(i/3), 1,1);
        }
<<<<<<< HEAD
        loadUser();
=======

        for (int i = 0; i < myCourses.size(); i++) {
            CourseCardBuilder course = new CourseCardBuilder(myCourses.get(i));
            Parent content = course.build();
            mycourseListPane.add(content , i % 3, round(i/3), 1,1);
        }
>>>>>>> 5cb2844c91b4a0c21cd7b7ecbf9d8a5ddf567bda
    }

    public void navToCoursePage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Online Examination System");
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("CoursesPage.fxml"));
        stage.setScene(new Scene(panel, 1000, 800));
        stage.show();
    }
<<<<<<< HEAD
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


=======
    void getCourse(){
        myCourses = courseDAO.getFilterAndPaging();
    }
    void getExams(){
        myExams = examDAO.getByStudentId(AppUtils.CURRENT_ROLE.id);
    }
>>>>>>> 5cb2844c91b4a0c21cd7b7ecbf9d8a5ddf567bda
}
