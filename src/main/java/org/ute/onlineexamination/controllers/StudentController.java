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
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.components.TestCardBuilder;
import org.ute.onlineexamination.daos.ExamDAO;
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
    ExamDAO examDAO;

    public StudentController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        myExams = examDAO.getByStudentId(AppUtils.CURRENT_ROLE.id);
        try {
            for (int i = 0; i < myExams.size(); i++) {
                TestCardBuilder test = new TestCardBuilder(myExams.get(i));
                Parent content = test.build();
                testListContent.add(content , i % 3, round(i/3), 1,1);
            }
            Pane course = new Pane();
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    course = FXMLLoader.load(MainApplication.class.getResource("CourseCard.fxml"));
                    course.setId("course "+ x + "-"+ y); ;
                    mycourseListPane.add(course, x, y, 1,1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void navToCoursePage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Online Examination System");
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("CoursesPage.fxml"));
        stage.setScene(new Scene(panel, 1000, 800));
        stage.show();
    }
}
