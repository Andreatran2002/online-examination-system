package org.ute.onlineexamination.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    String[] st = { "admin", "student", "teacher"};

    public ChoiceBox loginAs=  new ChoiceBox<String>(FXCollections.observableArrayList(st));
    @FXML
    private Button loginBtn;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;
    public LoginController(){
        loginAs.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            // if the item of the list is changed
            public void changed(ObservableValue ov, Number value, Number new_value)
            {

                // set the text for the label to the selected item
//                l1.setText(st[new_value.intValue()] + " selected");
            }
        });


    }

    public void userLogIn(ActionEvent event) throws IOException{
        checkLogIn(event);
    }

    private void checkLogIn(Event event) throws IOException{
        if (loginEmail.getText().toString().equals("admin") && loginPassword.getText().toString().equals("123")){
            wrongLogin.setText("Success!");
            switch (loginAs.getValue().toString()) {
                case "Admin":
                    AppUtils.changeScreen(event, "AdminPage.fxml");
                    break;
                case "Teacher":
                    AppUtils.changeScreen(event, "TeacherPage.fxml");
                    break;
                case "Student":
                    AppUtils.changeScreen(event, "StudentPage.fxml");
                    break;
            }
        }

        else if (loginEmail.getText().isEmpty() || loginPassword.getText().isEmpty()){
            wrongLogin.setText("Please enter your data");
        }
        else {
            wrongLogin.setText("Wrong email or password");
        }

    }

    public void navToRegisterPage(KeyEvent keyEvent) throws IOException {
        AppUtils.changeScreen(keyEvent, "RegisterPage.fxml");
    }

}
