package org.ute.onlineexamination.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.ute.onlineexamination.MainApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseController  implements Initializable {

    public GridPane courseListPane;

    public CourseController(){

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Pane course = new Pane();
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    course = FXMLLoader.load(MainApplication.class.getResource("CourseCard.fxml"));
                    course.setId("course "+ x + "-"+ y); ;
                    courseListPane.add(course, x, y, 1,1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
