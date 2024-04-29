package org.ute.onlineexamination.components;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Builder;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.controllers.CourseCardController;
import org.ute.onlineexamination.controllers.TestCardController;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Examination;

import java.io.IOException;

public class CourseCardBuilder implements Builder<Pane> {

    Course course;
    Callback callBackFunction;

    public CourseCardBuilder(Course course, Callback callBackFunction) {
        this.course = course;
        this.callBackFunction = callBackFunction;
    }

    @Override
    public Pane build() {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("CourseCard.fxml"));
        CourseCardController courseCardController = new CourseCardController(course, callBackFunction);
        loader.setController(courseCardController);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
