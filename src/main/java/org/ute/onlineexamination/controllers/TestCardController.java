package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class TestCardController  implements Initializable{

    public Label totalTime;
    public Label totalQuestions;
    public Label end;
    public Label start;
    public Label title;
    public Button testActionBtn;

    Examination examination;
    ExamDAO examDAO;
    Callback callback;
    public TestCardController(){

    }

    public TestCardController(Examination examination,Callback callback) {
        this.examination = examination;
        this.callback = callback;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        title.setText(examination.getName());
        start.setText(AppUtils.formatTime(examination.getStart()));
        end.setText(AppUtils.formatTime(examination.getEnd()));
        totalTime.setText(String.valueOf(examination.getTotal_minutes())+" min");
        totalQuestions.setText(String.valueOf(examination.questions.size() | 0 ) + " questions");
        setBtnState();
    }

    public void startTest(ActionEvent event) {
        AppUtils.showYesNoOption(event, "Start test", "Are you sure to begin test " + examination.getName(), new AlertActionInterface() {
            @Override
            public void action() throws IOException {
                navToTestPage();
            }
        });
        // TODO navigate to Test view
    }

    void navToTestPage() throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("TestPage.fxml"));
        TestController controller = new TestController(examination, new Callback() {
            @Override
            public Object call(Object param) {
                callback.call(true);
                return true;
            }
        });
        loader.setController(controller);
        Pane panel = loader.load();
        stage.setScene(new Scene(panel, 600, 500));
        stage.show();
        setBtnState();
    }
    void setBtnState(){

        Integer takeExamTimes = examDAO.checkTakeExamTimes(examination.getId(), AppUtils.CURRENT_ROLE.id);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (currentTimestamp.getTime() < examination.course.getStart().getTime()) {
            testActionBtn.setText("Not started");
            testActionBtn.setDisable(true);
        } else if (currentTimestamp.getTime() > examination.course.getEnd().getTime()) {
            testActionBtn.setText("Outdated");
            testActionBtn.setDisable(true);
        } else{
            if (takeExamTimes >= examination.getTime_retry()) {
                testActionBtn.setText("Done");
                testActionBtn.setDisable(true);
            }
            else if (takeExamTimes == 0 ){
                testActionBtn.setText("Start now");
                testActionBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        startTest(event);
                    }
                });
            } else{
                testActionBtn.setText("Retake");
                testActionBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        startTest(event);
                    }
                });
            }
        }

    }
}
