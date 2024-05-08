package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseStatusCardController implements Initializable {
    public Label name;
    public Label status;
    Course course;

    public void navToCourseDetail(ActionEvent event) {
        // TODO
    }

    public CourseStatusCardController(Course course){
        this.course = course;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.setText((course.getEnd().compareTo(AppUtils.getCurrentDateTime()) >= 0) ?
                (course.getStart().compareTo(AppUtils.getCurrentDateTime()) < 0) ?
                        "Learning" : "Not started" : "Finished");
        name.setText(course.getName());
    }
}
