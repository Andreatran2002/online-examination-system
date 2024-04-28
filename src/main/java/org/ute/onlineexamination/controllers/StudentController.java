package org.ute.onlineexamination.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
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
    ObservableList<Examination> myExams;
    ObservableList<Course> myCourses;
    ExamDAO examDAO;
    CourseDAO courseDAO;
    Integer currentTestPage = 0 ;
    Integer totalTestPage ;

    public StudentController(){

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

        for (int i = 0; i < myCourses.size(); i++) {
            CourseCardBuilder course = new CourseCardBuilder(myCourses.get(i));
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
    void getCourse(){
        myCourses = courseDAO.getFilterAndPaging();
    }
    void getExams(){
        myExams = examDAO.getByStudentId(AppUtils.CURRENT_ROLE.id);
    }
}
