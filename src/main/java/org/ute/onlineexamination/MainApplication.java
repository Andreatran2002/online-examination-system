package org.ute.onlineexamination;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginPage.fxml")));
        primaryStage.setScene(new Scene(root, 600, 400));
//        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }

}