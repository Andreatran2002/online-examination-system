package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.TeacherDAO;
import org.ute.onlineexamination.daos.UserDAO;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateTeacherController implements Initializable {
    public TextField textFieldFullName;
    public TextField textFieldPhoneNumber;
    public TextField textFieldTitle;
    public TextField textFieldAddress;
    Teacher teacher;
    TeacherDAO teacherDAO;
    User user;
    UserDAO userDAO;
    Parent panel ;
    Stage appStage1;

    public UpdateTeacherController() {
        teacherDAO = new TeacherDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUser();
        userDAO = new UserDAO();
    }
    public void loadUser(){
        user = AppUtils.CURRENT_USER;
        textFieldAddress.setText(user.getEmail());
        textFieldFullName.setText(user.getFull_name());
        textFieldPhoneNumber.setText(user.getMobile());
        teacher = teacherDAO.getByUserId(user.getId());
        textFieldTitle.setText(teacher.getTitle());
        textFieldAddress.setText(teacher.getAddress());
    }
    public void reset(ActionEvent event){
        textFieldFullName.setText(user.getFull_name());
        textFieldPhoneNumber.setText(user.getMobile());
        textFieldTitle.setText(teacher.getTitle());
        textFieldAddress.setText(teacher.getAddress());
    }
    public boolean checkVaild(){
        if (!textFieldFullName.getText().isEmpty()
                && !textFieldPhoneNumber.getText().isEmpty()
                && !textFieldTitle.getText().isEmpty()
                && !textFieldAddress.getText().isEmpty()){
            return true;
        }
        return false;
    }
    public void updateStudent(ActionEvent event){
        appStage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Boolean isValid = checkVaild();
        if (!isValid){
            return;
        }
        try{
            teacher = teacherDAO.getByUserId(user.getId());
            teacher.setUpdated_at(AppUtils.getCurrentDateTime());
            teacher.setTitle(textFieldTitle.getText());
            teacher.setAddress(textFieldAddress.getText());
            System.out.println(teacher);
            user.setFull_name(textFieldFullName.getText());
//            user.setPassword_hash(BCrypt.hashpw(textFieldPassword.getText(),BCrypt.gensalt(12)));
            user.setMobile(textFieldPhoneNumber.getText());
            user.setUpdated_at(AppUtils.getCurrentDateTime());
            userDAO.update(user);
            teacherDAO.update(teacher);
            AppUtils.showInfo(event, "Update Teacher success", "Update teacher with " + user.getEmail() + "successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });

            navToHomePage("TeacherPage",user);

        }catch (Exception e){
            AppUtils.showAlert(event,"Update teacher false",e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    void navToHomePage(String page,User user) throws IOException {
        panel = FXMLLoader.load(MainApplication.class.getResource(page+".fxml"));
        Scene scene = new Scene(panel, 1000, 800);
        appStage1.setScene(scene);
        appStage1.show();
    }
}
