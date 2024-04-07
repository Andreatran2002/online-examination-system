package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCourseController implements Initializable {
    @FXML
    private TextField name ;
    @FXML
    private TextField description ;
    @FXML
    private DatePicker from ;
    @FXML
    private DatePicker to ;
    @FXML
    private ChoiceBox<String> category;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> ts = FXCollections.observableArrayList();
        ts.addAll("Artificial Intelligence", "Machine Learning", "Cybersecurity", "Digital Marketing", "Photography", "Music Production", "Language Learning", "Personal Development", "Finance", "Health & Fitness");
        category.setItems(ts);
    }

    public void onUpdateNewCourse(ActionEvent event) {
    }
}
