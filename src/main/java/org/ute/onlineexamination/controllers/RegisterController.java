package org.ute.onlineexamination.controllers;


import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.StudentDAO;
import org.ute.onlineexamination.daos.TeacherDAO;
import org.ute.onlineexamination.daos.UserDAO;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AppUtils;

public class RegisterController {


    public TextField registerFullName;
    public PasswordField registerPassword;
    public TextField registerEmail;
    public ChoiceBox registerAs;
    UserDAO userDAO = new UserDAO();
    TeacherDAO teacherDAO = new TeacherDAO();
    StudentDAO studentDAO = new StudentDAO();

    public void userRegister(ActionEvent event) {

        String fullName = registerFullName.getText();
        String email = registerEmail.getText();
        String password = registerPassword.getText();
        try{

            userDAO.save(new User(fullName, email, password));
            User user = userDAO.getByEmail(email);
            switch (registerAs.getValue().toString()) {
                case "Teacher":
                    teacherDAO.save(new Teacher(user.getId()));
                    break;
                case "Student":
                    studentDAO.save(new Student(user.getId()));
                    break;
                default:
                    break;
            }

        }catch (Exception e){
            AppUtils.showAlert(Alert.AlertType.ERROR,event,"Lỗi trong quá trình tạo user", e.getMessage());
        }


    }

    public void navToLoginPage(MouseEvent mouseEvent) {
        try {
            AppUtils.changeScreen(mouseEvent, "LoginPage.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}