package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.StudentDAO;
import org.ute.onlineexamination.daos.UserDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateStudentController implements Initializable{

    public Label labelEmail;
    public Label labelRole;
    public TextField textFieldEmailAddress;
    public TextField textFieldPhoneNumber;
    public TextField textFieldPassword;
    UserDAO userDAO;
    StudentDAO studentDAO;


    public UpdateStudentController(){

    }
    @Override
    public void initialize(URL Location, ResourceBundle resources){

    }

    public void onUpdateStudent (ActionEvent event){

    }

}
