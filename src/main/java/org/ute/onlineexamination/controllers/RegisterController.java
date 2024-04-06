package org.ute.onlineexamination.controllers;


import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.kordamp.bootstrapfx.scene.layout.Panel;
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
    public Label wrongRegister;
    UserDAO userDAO;
    TeacherDAO teacherDAO;
    StudentDAO studentDAO;
    Stage appStage;
    Panel panel;


    public RegisterController(){
        teacherDAO = new TeacherDAO();
        userDAO = new UserDAO();
        studentDAO = new StudentDAO();
    }

    Boolean checkInput(){
        return !registerEmail.getText().isEmpty() && !registerPassword.getText().isEmpty() && !registerFullName.getText().isEmpty();
    }
    void reset (){
        registerEmail.setText("");
        registerFullName.setText("");
        registerPassword.setText("");
    }

    public void userRegister(ActionEvent event) {
        Boolean isInputValid = checkInput();
        if (!isInputValid ){
            wrongRegister.setText("Invalid input. Please try again!");
            return ;
        }
        if (!wrongRegister.getText().isEmpty()){
            wrongRegister.setText("");
        }
        String fullName = registerFullName.getText();
        String email = registerEmail.getText();
        String password = registerPassword.getText();

        User user = userDAO.getByEmail(email);
        System.out.println(user.getEmail());
        if (user.getEmail() != null){
            wrongRegister.setText("Email account exist. Please try different email !");
            return ;
        }
        try{
            userDAO.save(new User(fullName, email, password));
            user = userDAO.getByEmail(email);
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
            reset();
        }catch (Exception e){
            AppUtils.showAlert(Alert.AlertType.ERROR,event,"Lỗi trong quá trình tạo user", e.getMessage());
        }
    }

    public void navToLoginPage(MouseEvent mouseEvent) {
        try {
            appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            panel = FXMLLoader.load(MainApplication.class.getResource("LoginPage.fxml"));
            appStage.setScene(new Scene(panel, 600, 400));
            appStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}