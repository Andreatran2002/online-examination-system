package org.ute.onlineexamination;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("StudentPage.fxml")));
        primaryStage.setTitle("Online Examination System");
//        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
this.sceneChangeHandler(primaryStage);

    }

    public void sceneChangeHandler(Stage primaryStage) {
        primaryStage.sceneProperty().addListener((observable, old, newV) -> {
            System.out.println("sceen change");

            System.out.println(old);
            System.out.println(newV);

        });
    }


}