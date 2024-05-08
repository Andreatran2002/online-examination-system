package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

public class NewCourseController implements Initializable {
    public DatePicker fromDate;
    public TextField fromTime;
    public DatePicker toDate;
    public TextField toTime;
    @FXML
    private TextField name ;
    @FXML
    private TextField description ;
    @FXML
    private ChoiceBox<String> category;
    private CourseDAO courseDAO ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseDAO = new CourseDAO();
        ObservableList<String> ts = FXCollections.observableArrayList();
        ts.addAll("Artificial Intelligence", "Machine Learning", "Cybersecurity", "Digital Marketing", "Photography", "Music Production", "Language Learning", "Personal Development", "Finance", "Health & Fitness");
        category.setItems(ts);
    }

    boolean checkValidData(){
        // TODO: From date before to date

        // TODO: not empty name , categroy , description

        return true;
    }

    public void onCreateNewCourse(ActionEvent event) {
        Boolean isValid = checkValidData();
        if (!isValid) {
            return ;
        }
        try {
            Course course = new Course();
            course.setCategory(category.getValue());
            course.setStart(AppUtils.fromDateAndTime(fromDate.getValue(),fromTime.getText()));
            course.setEnd(AppUtils.fromDateAndTime(toDate.getValue(),toTime.getText()));
            course.setName(name.getText());
            course.setDescription(description.getText());
            courseDAO.save(course);
            AppUtils.showInfo(event, "Create course success", "Create course " + name.getText() + "successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage   appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });
        }catch (Exception e){
            AppUtils.showAlert(event,"Create course false",e.getMessage());
        }
    }
}
