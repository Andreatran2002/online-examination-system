package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ute.onlineexamination.daos.StudentUserDAO;
import org.ute.onlineexamination.daos.UserDAO;
import org.ute.onlineexamination.models.Student;
import org.ute.onlineexamination.models.StudentUser;
import org.ute.onlineexamination.models.User;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    private TableView studentTableView;

    private ObservableList<StudentUser> studentUserObservableList = FXCollections.observableArrayList();

    UserDAO userDAO;

    StudentUserDAO studentUserDAO;

    public TableColumn<StudentUser, Integer> studentStudentId;
    public TableColumn<StudentUser, Integer> studentUserId;
    public TableColumn<StudentUser, String> studentFullName;
    public TableColumn<StudentUser, String> studentMobile;
    public TableColumn<StudentUser, String> studentEmail;
    public TableColumn<StudentUser, Timestamp> studentLastLogin;
    public TableColumn<StudentUser, Timestamp> studentCreatedAt;
    public TableColumn<StudentUser, Timestamp> studentUpdatedAt;
    public TableColumn<StudentUser, Timestamp> studentDeletedAt;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDAO = new UserDAO();
        studentUserDAO = new StudentUserDAO();
        initStudentUser();
    }
    public AdminController(){

    }
    public void initStudentUser (){
        studentStudentId.setCellValueFactory(new PropertyValueFactory<StudentUser, Integer>("id"));
        studentUserId.setCellValueFactory(new PropertyValueFactory<StudentUser, Integer>("id"));
        studentFullName.setCellValueFactory(new PropertyValueFactory<StudentUser, String>("full_name"));
        studentMobile.setCellValueFactory(new PropertyValueFactory<StudentUser, String>("mobile"));
        studentEmail.setCellValueFactory(new PropertyValueFactory<StudentUser, String>("email"));
        studentLastLogin.setCellValueFactory(new PropertyValueFactory<StudentUser, Timestamp>("last_login"));
        studentCreatedAt.setCellValueFactory(new PropertyValueFactory<StudentUser, Timestamp>("created_at"));
        studentUpdatedAt.setCellValueFactory(new PropertyValueFactory<StudentUser, Timestamp>("updated_at"));
        studentDeletedAt.setCellValueFactory(new PropertyValueFactory<StudentUser, Timestamp>("deleted_at"));
        studentUserObservableList = studentUserDAO.getAll();
        if (!studentUserObservableList.isEmpty()) {
            studentTableView.getItems().addAll(studentUserObservableList);
            System.out.println("1");
        }
        else
            System.out.println("0");
    }

}
