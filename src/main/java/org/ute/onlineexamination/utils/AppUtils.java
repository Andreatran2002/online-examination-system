package org.ute.onlineexamination.utils;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.ute.onlineexamination.MainApplication;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppUtils {
    public AppUtils(){

    }

    public static void changeScreen(Event event , String page) throws IOException {
        Scene scene = ((Node)event.getSource()).getScene();
        Parent panel = FXMLLoader.load(MainApplication.class.getResource(page));
        scene.setRoot(panel);
    }

    public static void showAlert(Alert.AlertType alertType, Event event , String title, String message) {
        Scene scene = ((Node)event.getSource()).getScene();
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(scene.getWindow());
        alert.show();
    }

    public static Timestamp getCurrentDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

}
