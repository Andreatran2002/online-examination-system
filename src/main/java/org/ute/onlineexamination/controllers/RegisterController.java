package org.ute.onlineexamination.controllers;


import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.UserDAO;
import org.ute.onlineexamination.models.User;

public class RegisterController {


    public TextField registerFullName;
    public PasswordField registerPassword;
    public TextField registerEmail;

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void userRegister(ActionEvent event) {

        String fullName = registerFullName.getText();
        String email = registerEmail.getText();
        String password = registerPassword.getText();

        UserDAO userDAO = new UserDAO();
        userDAO.register(new User(fullName, email, password));

    }

    public void navToLoginPage(MouseEvent mouseEvent) {
        MainApplication m = new MainApplication();
        try {
            m.changeScene("LoginPage.fxml",600,400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}