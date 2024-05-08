package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.daos.TeacherDAO;
import org.ute.onlineexamination.daos.UserDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.Teacher;
import org.ute.onlineexamination.models.User;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.ResourceBundle;

public class CourseCardController implements Initializable{

    public Label end;
    public Label start;
    public Label duration;
    public Label teacherName;
    public Label name;
    public Button courseCardBtn;
    public Callback callBackFunction;

    Course course;
    TeacherDAO teacherDAO;
    UserDAO userDAO;

    CourseDAO courseDAO;
    public CourseCardController(){

    }

    public CourseCardController(Course course,Callback callBackFunction) {
        this.course = course;
        this.callBackFunction = callBackFunction;
    }




    public void initialize(URL location, ResourceBundle resources) {
        teacherDAO = new TeacherDAO();
        userDAO = new UserDAO();
        courseDAO = new CourseDAO();
        name.setText(course.getName());
        start.setText(AppUtils.formatTime(course.getStart()));
        end.setText(AppUtils.formatTime(course.getEnd()));
        duration.setText(String.valueOf(AppUtils.between( course.getStart(), course.getEnd()))+" days");
        Teacher teacher = teacherDAO.get(course.getTeacher_id()).get();
        User user  = userDAO.get(teacher.getUser_id()).get();
        teacherName.setText(user.getFull_name());
        setBtnState();

    }
    void enrollCourse(Event event) {
        AppUtils.showYesNoOption(event, "Enroll new course", "Are you sure that you want to enroll course " + course.getName(), new AlertActionInterface() {
            @Override
            public void action() throws IOException {
                Integer id =  courseDAO.enrollCourse(course.getId());
                if (id != -1){
                    setBtnState();
                    AppUtils.showInfo(event, "Enroll new course success", "Enroll course " + course.getName() + " success", new AlertActionInterface() {
                        @Override
                        public void action() throws IOException {
                            callBackFunction.call(true);
                        }
                    });
                }


            }
        });
    }

    void setBtnState(){
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (currentTimestamp.getTime() < course.getStart().getTime()) {
            courseCardBtn.setText("Not started");
            courseCardBtn.setDisable(true);
        } else if (currentTimestamp.getTime() > course.getEnd().getTime()) {
            courseCardBtn.setText("Outdated");
            courseCardBtn.setDisable(true);
        } else{
            Boolean hasEnrolled = courseDAO.checkEnroll(course.getId());
            if (hasEnrolled) {
                courseCardBtn.setText("Enrolled");
                courseCardBtn.setDisable(true);
            }
            else {
                courseCardBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        enrollCourse(event);
                    }
                });
            }
        }
    }

}
