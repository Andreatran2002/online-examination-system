package org.ute.onlineexamination.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Builder;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.controllers.CourseCardController;
import org.ute.onlineexamination.controllers.CourseStatusCardController;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;

public class CourseStatusBuilder implements Builder<Pane> {

    Course course;
    Callback callBackFunction;

    public CourseStatusBuilder(Course course) {
        this.course = course;
//        this.callBackFunction = callBackFunction;
    }

    @Override
    public Pane build() {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("CourseStatusCard.fxml"));
        CourseStatusCardController courseCardController = new CourseStatusCardController(course);
        loader.setController(courseCardController);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
