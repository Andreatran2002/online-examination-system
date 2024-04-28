package org.ute.onlineexamination.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TestCardController  implements Initializable{

    public Label totalTime;
    public Label totalQuestions;
    public Label enrolledTotal;
    public Label end;
    public Label start;
    public Label title;

    Examination examination;
    public TestCardController(){

    }

    public TestCardController(Examination examination) {
        this.examination = examination;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public void initialize(URL location, ResourceBundle resources) {
        title.setText(examination.getName());
        start.setText(AppUtils.formatTime(examination.getStart()));
        end.setText(AppUtils.formatTime(examination.getEnd()));
        totalTime.setText(String.valueOf(examination.getTotal_minutes())+" min");
        totalQuestions.setText(String.valueOf(examination.questions.size() | 0 ) + " questions");
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
        TestController controller = new TestController(examination);
        loader.setController(controller);
        Pane panel = loader.load();
        stage.setScene(new Scene(panel, 600, 500));
        stage.show();
    }
}
