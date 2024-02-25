package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.ute.onlineexamination.MainApplication;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginBtn;
    @FXML
    private ChoiceBox loginAs;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;
    public LoginController(){
        loginAs = new ChoiceBox<>();
        loginAs.getItems().addAll("admin","student","teacher");
        loginAs.setOnAction( (event) ->
        {
            System.out.println(event.toString());
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
                    m.changeScene("AdminPage.fxml");
                    break;
                case "Teacher":
                    m.changeScene("TeacherPage.fxml");
                    break;
                case "Student":
                    m.changeScene("StudentPage.fxml");
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
}
