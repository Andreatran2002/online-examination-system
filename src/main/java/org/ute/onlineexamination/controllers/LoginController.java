package org.ute.onlineexamination.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.StudentDAO;
import org.ute.onlineexamination.daos.TeacherDAO;
import org.ute.onlineexamination.daos.UserDAO;
import org.ute.onlineexamination.models.RoleData;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginController {
    public ChoiceBox loginAs;
    @FXML
    private Button loginBtn;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField loginEmail;
    @FXML
    private PasswordField loginPassword;
    Parent panel ;
    UserDAO userDAO ;
    Stage appStage;
    TeacherDAO teacherDAO;
    StudentDAO studentDAO;
    public LoginController(){
        userDAO = new UserDAO();
        teacherDAO = new TeacherDAO();
        studentDAO = new StudentDAO();
    }

    void navToHomePage(String page,User user) throws IOException {
        panel = FXMLLoader.load(MainApplication.class.getResource(page+".fxml"));
        Scene scene = new Scene(panel, 1000, 800);
        appStage.setScene(scene);
        appStage.show();
        userDAO.updatelast_login(user);
    }

    public void userLogIn(ActionEvent event) throws IOException{
        appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        wrongLogin.setText("");
        User user = userDAO.getByEmail(loginEmail.getText());
        if (Objects.equals(user.getEmail(), "")){
            wrongLogin.setText("Account not exist. Please try again !");
            return ;
        }

        if (user.checkPassword(loginPassword.getText())) {
            user.setLast_login(AppUtils.getCurrentDateTime());
            Timestamp timestamp = user.getLast_login();
            System.out.println(timestamp);
            switch (loginAs.getValue().toString()) {
                case "Admin":
                    if (!user.getIs_admin()){
                        wrongLogin.setText("This account is not admin");
                        return ;
                    }
                    AppUtils.saveUser(user, new RoleData(user.getId(), "Admin"));
                    navToHomePage("AdminPage",user);
                    break;
                case "Teacher":
                    Teacher teacher = teacherDAO.getByUserId(user.getId());
                    if (teacher.getCreated_at()==null){
                        wrongLogin.setText("This account is not teacher");
                        return ;
                    }
                    AppUtils.saveUser(user, new RoleData(teacher.getId(), "Teacher"));
                    navToHomePage("TeacherPage",user);
                    break;
                case "Student":
                    Student student = studentDAO.getByUserId(user.getId());
                    if (student.getCreated_at()==null){
                        wrongLogin.setText("This account is not student");
                        return ;
                    }
                    AppUtils.saveUser(user, new RoleData(student.getId(), "Student"));
                    navToHomePage("StudentPage",user);
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

    public void navToRegisterPage(MouseEvent event) throws IOException {
        appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        panel = FXMLLoader.load(MainApplication.class.getResource("RegisterPage.fxml"));
        appStage.setScene(new Scene(panel, 600, 400));
        appStage.show();
    }
}
