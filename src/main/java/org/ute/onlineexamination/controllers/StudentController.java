package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.ute.onlineexamination.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    public GridPane testListContent;
    public Pagination myTestPagination;
    public GridPane mycourseListPane;

    public StudentController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Pane test = new Pane();
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    test = FXMLLoader.load(MainApplication.class.getResource("TestCard.fxml"));
                    test.setId("test "+ x + "-"+ y); ;
                    testListContent.add(test, x, y, 1,1);
                }
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
    public void navToUpdateStudentPage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Update Student");
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("UpdateStudentPage.fxml"));
        stage.setScene(new Scene(panel, 400, 400));
        stage.show();
    }
}
