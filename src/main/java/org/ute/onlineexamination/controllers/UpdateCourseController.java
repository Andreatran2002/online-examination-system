package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateCourseController implements Initializable {
    public DatePicker toDate;
    public TextField toTime;
    public DatePicker fromDate;
    public TextField fromTime;
    public BorderPane updateCoursePane;
    @FXML
    private TextField name ;
    @FXML
    private TextField description ;
    @FXML
    private ChoiceBox<String> category;
    private CourseDAO courseDAO;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    Course course ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseDAO = new CourseDAO();
        ObservableList<String> ts = FXCollections.observableArrayList();
        ts.addAll("Artificial Intelligence", "Machine Learning", "Cybersecurity", "Digital Marketing", "Photography", "Music Production", "Language Learning", "Personal Development", "Finance", "Health & Fitness");
        category.setItems(ts);
        System.out.println(course); // Prints the Scene
        name.setText(course.getName());
        category.setValue(course.getCategory());
        description.setText(course.getDescription());
        fromDate.setValue(course.getStart().toLocalDateTime().toLocalDate());
        toDate.setValue(course.getEnd().toLocalDateTime().toLocalDate());
        fromTime.setText(course.getStart().toLocalDateTime().format(DateTimeFormatter.ofPattern("hh:mm")));
        toTime.setText(course.getEnd().toLocalDateTime().format(DateTimeFormatter.ofPattern("hh:mm")));
    }

    boolean checkValidData(){
        // TODO: From date before to date

        // TODO: not empty name , categroy , description

        return true;
    }


    public void onUpdateNewCourse(ActionEvent event) {
        Boolean isValid = checkValidData();
        if (!isValid) {
            return ;
        }
        try {
            course.setCategory(category.getValue());
            course.setStart(AppUtils.fromDateAndTime(fromDate.getValue(),fromTime.getText()));
            course.setEnd(AppUtils.fromDateAndTime(toDate.getValue(),toTime.getText()));
            course.setName(name.getText());
            course.setDescription(description.getText());
            courseDAO.update(course);
            AppUtils.showInfo(event, "Update course success", "Create course " + name + "successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });
        }catch (Exception e){
            AppUtils.showAlert(event,"Update course false",e.getMessage());
        }
    }
}
