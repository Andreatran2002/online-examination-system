package org.ute.onlineexamination.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Builder;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.controllers.CourseCardController;
import org.ute.onlineexamination.controllers.TestCardController;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Examination;

import java.io.IOException;

public class CourseCardBuilder implements Builder<Pane> {

    Course course;

    public CourseCardBuilder(Course course) {
        this.course = course;
    }

    @Override
    public Pane build() {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("CourseCard.fxml"));
        CourseCardController courseCardController = new CourseCardController(course);
        loader.setController(courseCardController);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
