package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseStatusCardController implements Initializable {
    public Label name;
    public Label status;
    Course course;

    public void navToCourseDetail(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("StudentCourseDetailPage.fxml"));
        StudentCourseDetailController controller = new StudentCourseDetailController();
        controller.setCourse(course);
        loader.setController(controller);
        Pane panel = loader.load();
        stage.setScene(new Scene(panel, 1000, 800));
        stage.show();
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
