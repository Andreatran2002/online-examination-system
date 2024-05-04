package org.ute.onlineexamination.utils;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.controllers.DetailCourseController;
import org.ute.onlineexamination.models.Course;

import java.io.IOException;

public class AppNav {
    public void toDetailCourse(Course course, EventHandler<WindowEvent> onHiding) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("DetailCoursePage.fxml"));
        DetailCourseController controller = new DetailCourseController();
        controller.setCourse(course);
        loader.setController(controller);
        stage.setScene(new Scene(loader.load(), 1000, 800));
        stage.setOnHiding(onHiding);
        stage.show();
    }
}
