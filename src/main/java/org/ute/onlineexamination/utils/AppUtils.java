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
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
            try {
                action.action();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
            try {
                okeAction.action();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }



    public static Timestamp getCurrentDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        return timestamp;
    }

    public static Time getTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        Time time = convertToTime(timestamp);
        return time;
    }
    public static Time convertToTime(Timestamp timestamp) {
        long milliseconds = timestamp.getTime();
        return new Time(milliseconds);
    }
    public  static String formatTime(Timestamp time){
        Date date = new Date(time.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, h:mm a");
        return dateFormat.format(date);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static long between ( Timestamp startTimestamp , Timestamp endTimestamp){
        long millisecondsDifference = endTimestamp.getTime() - startTimestamp.getTime();

        // Convert milliseconds to days, hours, minutes, and seconds
        long seconds = millisecondsDifference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        return days;
    }
}
