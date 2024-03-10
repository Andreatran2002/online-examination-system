package org.ute.onlineexamination.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.models.User;

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
        checkLogIn();
    }

    private void checkLogIn() throws IOException{
        MainApplication m = new MainApplication();
        if (loginEmail.getText().toString().equals("admin") && loginPassword.getText().toString().equals("123")){
            wrongLogin.setText("Success!");
            switch (loginAs.getValue().toString()) {
                case "Admin":
                    m.changeScene("AdminPage.fxml", 1200,600);
                    break;
                case "Teacher":
                    m.changeScene("TeacherPage.fxml", 1200,600);
                    break;
                case "Student":
                    m.changeScene("StudentPage.fxml", 1200,600);
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

    public void navToRegisterPage(KeyEvent keyEvent)  {
        MainApplication m = new MainApplication();
        try {
            m.changeScene("RegisterPage.fxml", 600,400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
