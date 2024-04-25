package org.ute.onlineexamination.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.ute.onlineexamination.components.TestQuestionBuilder;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.daos.QuestionDAO;
import org.ute.onlineexamination.daos.TakeExamDAO;
import org.ute.onlineexamination.models.ExamQuestion;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.Question;
import org.ute.onlineexamination.models.TakeExam;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController implements Initializable {
    public Label timeLeft;
    public Label title;
    public ListView questionsView;
    public ObservableList<Pane> questionList;
    public ObservableList<TestQuestionBuilder> testQuestionBuilders;
    Examination examination;
    ExamDAO examDAO;
    private Timeline timeline;
    QuestionDAO questionDAO;
    TakeExamDAO takeExamDAO;
    private int secondsRemaining ;
    public TestController(Examination examination){
        this.examination = examination;
    }

    public void finishTest(ActionEvent event) {
        // TODO: Nopj bai + tinh diem
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        takeExamDAO = new TakeExamDAO();
        questionDAO = new QuestionDAO();
        questionList = FXCollections.observableArrayList();
        title.setText(examination.getName());
        secondsRemaining = examination.getTotal_minutes()*60;

        // TODO: Create take_exam

        Integer takeExamId = takeExamDAO.save(new TakeExam(AppUtils.CURRENT_ROLE.id, examination.getId(),AppUtils.getCurrentDateTime()));

        for (int i = 0; i <  examination.questions.size(); i++) {
            ExamQuestion eq = examination.questions.get(i);
            eq.question = questionDAO.get(examination.questions.get(i).getQuestion_id()).get();
            examination.questions.set(i, eq);
            TestQuestionBuilder testQuestionBuilder =  new TestQuestionBuilder(takeExamId,examination.questions.get(i));
            testQuestionBuilders.add(testQuestionBuilder);
            questionList.add(testQuestionBuilder.build());
        }
        questionsView.setItems(questionList);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                secondsRemaining--;
                updateTimerLabel();
                if (secondsRemaining <= 0) {
                    timeline.stop();
                    mark();
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    void mark(){
        // TODO: hien thi diem + thoat
        for (int i = 0; i < testQuestionBuilders.size(); i++) {
            testQuestionBuilders.get(i).mark();
        }
    }

    private void updateTimerLabel() {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;
        String time = String.format("Time: %02d:%02d", minutes, seconds);
        timeLeft.setText(time);
    }

}
