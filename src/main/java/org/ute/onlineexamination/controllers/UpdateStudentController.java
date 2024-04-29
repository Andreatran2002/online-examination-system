package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.StudentDAO;
import org.ute.onlineexamination.daos.UserDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateStudentController implements Initializable{

    public Label labelEmail;
    public Label labelRole;
    public TextField textFieldFullName;
    public TextField textFieldPhoneNumber;
    UserDAO userDAO;
    StudentDAO studentDAO;
    User user;

    User user1 = AppUtils.CURRENT_USER;
    Student student;
    public UpdateStudentController(){
        studentDAO = new StudentDAO();
    }
    public User getUser() {return user;}

    public Student getStudent() {return student;}

    public void setUser(User user) {
        this.user = user;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public void initialize(URL Location, ResourceBundle resources){
        loadUser();
        userDAO = new UserDAO();
    }

    public void onUpdateStudent (ActionEvent event){

    }
    public void loadUser(){
        labelEmail.setText(user1.getEmail());
        labelRole.setText("Student");
        textFieldFullName.setText(user1.getFull_name());
        textFieldPhoneNumber.setText(user1.getMobile());
    }
    public void reset(ActionEvent event){
        textFieldFullName.setText(user1.getFull_name());
        textFieldPhoneNumber.setText(user1.getMobile());
    }
    public boolean checkVaild(){
        if (!textFieldFullName.getText().isEmpty() && !textFieldPhoneNumber.getText().isEmpty()){
            return true;
        }
        return false;
    }
    public void updateStudent(ActionEvent event){
        Boolean isValid = checkVaild();
        if (!isValid){
            return;
        }
        try{
            student = studentDAO.getByUserId(user1.getId());
            student.setUpdated_at(AppUtils.getCurrentDateTime());
            System.out.println(student);
            user1.setFull_name(textFieldFullName.getText());
//            user1.setPassword_hash(BCrypt.hashpw(textFieldPassword.getText(),BCrypt.gensalt(12)));
            user1.setMobile(textFieldPhoneNumber.getText());
            user1.setUpdated_at(AppUtils.getCurrentDateTime());
            userDAO.update(user1);
            studentDAO.update(student);
            AppUtils.showInfo(event, "Update Student success", "Update student with " + user1.getEmail() + "successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });

        }catch (Exception e){
            AppUtils.showAlert(event,"Update student false",e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
