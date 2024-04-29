package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.ute.onlineexamination.daos.UserDAO;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    public Label labelEmail;
    public PasswordField passwordFieldMatKhauCu;
    public PasswordField passwordFieldMatKhauMoi;
    public PasswordField passwordFieldNhapLaiMatKhauMoi;

    UserDAO userDAO;
    User user = AppUtils.CURRENT_USER;

    Parent panel ;
    Stage appStage;


    public void initialize(URL Location, ResourceBundle resources){
        userDAO = new UserDAO();
        loadUser();
    }
    public void loadUser(){
        labelEmail.setText(user.getEmail());
    }
    public void reset(ActionEvent event){
        passwordFieldMatKhauCu.setText("");
        passwordFieldMatKhauMoi.setText("");
        passwordFieldNhapLaiMatKhauMoi.setText("");
    }

    public boolean checkVaild(){
        if (!passwordFieldMatKhauCu.getText().isEmpty()
                && !passwordFieldMatKhauMoi.getText().isEmpty()
                && !passwordFieldNhapLaiMatKhauMoi.getText().isEmpty()){
            System.out.println(1);
            return true;
        }
        return false;
    }
    public boolean checkInput(){
        if(user.checkPassword(passwordFieldMatKhauCu.getText())
                && passwordFieldMatKhauMoi.getText().equals(passwordFieldNhapLaiMatKhauMoi.getText())){
            System.out.println(2);
            return true;
        }
        return false;
    }
    public void changePassword(ActionEvent event){
        appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Boolean isValid = checkVaild();
        Boolean isValid1 = checkInput();
        if (!isValid || !isValid1){
            System.out.println("Lỗi input");
            System.out.println(user.checkPassword(passwordFieldMatKhauCu.getText()));
            System.out.println(passwordFieldMatKhauMoi.getText());
            System.out.println(passwordFieldNhapLaiMatKhauMoi.getText());
            return;
        }
        try{
            user.setPassword_hash(BCrypt.hashpw(passwordFieldMatKhauMoi.getText(),BCrypt.gensalt(12)));
            userDAO.changePassword(user);
            AppUtils.showInfo(event, "Change password success", "Change password with " + user.getEmail() + "successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });
        }catch (Exception e){
            AppUtils.showAlert(event,"Change password false",e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
