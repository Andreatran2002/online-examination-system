package org.ute.onlineexamination.utils;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.models.RoleData;
import org.ute.onlineexamination.models.User;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;



public class AppUtils {

    public static String APP_TITLE = "Online Examination System";
    public static User CURRENT_USER;
    public static RoleData CURRENT_ROLE;
    public static void saveUser(User user , RoleData role ) {

        CURRENT_USER = user ;
        CURRENT_ROLE = role;
    }
    public AppUtils(){

    }

    public static void changeScreen(Event event , String page) throws IOException {
        Scene scene = ((Node)event.getSource()).getScene();
        Parent panel = FXMLLoader.load(MainApplication.class.getResource(page));
        scene.setRoot(panel);
    }


    static public Date fromLdt(LocalDateTime ldt) {
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        GregorianCalendar cal = GregorianCalendar.from(zdt);
        return cal.getTime();
    }
    static  public Timestamp fromDateAndTime(LocalDate date, String timeString){
        String[] fromTimeData = timeString.split(":");
        Date fromDateTime = AppUtils.fromLdt(date.atTime(Integer.parseInt(fromTimeData[0]),Integer.parseInt(fromTimeData[1]))) ;
        return new Timestamp(fromDateTime.getTime());
    }

    public static void showAlert( Event event , String title, String message) {
        Scene scene = ((Node)event.getSource()).getScene();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(scene.getWindow());
        alert.show();
    }

    public static  void showInfo(Event event , String title, String message , AlertActionInterface action ){
        Scene scene = ((Node)event.getSource()).getScene();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(scene.getWindow());
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            action.action();
        }
    }
    public static  void showYesNoOption(Event event , String title, String message , AlertActionInterface okeAction ){
        Scene scene = ((Node)event.getSource()).getScene();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(scene.getWindow());
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            okeAction.action();
        }

    }



    public static Timestamp getCurrentDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

}
