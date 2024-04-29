package org.ute.onlineexamination.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Builder;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.controllers.TestCardController;
import org.ute.onlineexamination.daos.TakeExamAnswerDAO;
import org.ute.onlineexamination.models.*;

import java.io.IOException;
import java.util.List;

public class TestQuestionBuilder implements Builder<Pane> {

    ExamQuestion examQuestion;
    TakeExamAnswer takeExamAnswer;
    Integer takeExamId;
    TakeExamAnswerDAO takeExamAnswerDAO;

    public TestQuestionBuilder(Integer takeExamId,  ExamQuestion examQuestion) {
        this.examQuestion = examQuestion;
        this.takeExamId = takeExamId;
        this.takeExamAnswerDAO = new TakeExamAnswerDAO();
    }

    ObservableList<RadioButton> listOptions;

    @Override
    public Pane build() {
        VBox questionPane = new VBox();
        questionPane.setSpacing(10);
        questionPane.setPadding(new Insets(10));

        Label questionLabel = new Label(examQuestion.question.getContent());
        questionPane.getChildren().add(questionLabel);
        listOptions = FXCollections.observableArrayList();

        List<Answer> answers = examQuestion.question.getAnswers();
        for (Answer answer : answers) {
            RadioButton radioButton = new RadioButton(answer.getContent());
            radioButton.setUserData(answer);
            listOptions.add(radioButton);
        }
        questionPane.getChildren().addAll(listOptions);

        return questionPane;
    }

    public void mark(){
        for (int i = 0; i < listOptions.size(); i++) {
            if (listOptions.get(i).isSelected()){
                Answer answer = (Answer) listOptions.get(i).getUserData();
                takeExamAnswer = new TakeExamAnswer();
                takeExamAnswer.setTake_exam_id(takeExamId);
                takeExamAnswer.setAnswer_id(answer.getId());
                takeExamAnswer.setExam_question_id(this.examQuestion.getId());
                takeExamAnswerDAO.save(takeExamAnswer);
            }
        }


    }

}
