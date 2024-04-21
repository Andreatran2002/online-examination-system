package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.daos.*;
import org.ute.onlineexamination.models.*;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class NewTestController implements Initializable {

    public TextField title;
    public TextField description;
    public ChoiceBox course;
    public DatePicker fromDate;
    public TextField fromTime;
    public DatePicker toDate;
    public TextField toTime;
    public TextField timeRetry;
    public RadioButton scoringHighest;
    public RadioButton scoringAverage;
    public RadioButton noLimit;
    ExamDAO examDAO;
    ExamQuestionDAO examQuestionDAO;

    CourseDAO courseDAO;
    ObservableList<Course> coursesByTeacher;
    ObservableList<ExamQuestion> examQuestions;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseDAO = new CourseDAO();
        examDAO = new ExamDAO();
        examQuestionDAO = new ExamQuestionDAO();
        coursesByTeacher= courseDAO.getByTeacherId(AppUtils.CURRENT_ROLE.id);

        ObservableList<String> cOption =  FXCollections.observableArrayList();
        for (int i = 0; i < coursesByTeacher.size(); i++) {
            cOption.add(coursesByTeacher.get(i).getName());
        }
        course.setItems(cOption);


    }

    boolean checkValidData(){
        // TODO: From date before to date

        // TODO: not empty name , categroy , description

        return true;
    }

    public void onCreateNewTest(ActionEvent event) {
        Boolean isValid = checkValidData();
        if (!isValid) {
            return ;
        }

        try {
            Examination exam = new Examination();
            exam.setName(title.getText());
            FilteredList<Course> courseSelected = coursesByTeacher.filtered(new Predicate<Course>() {
                @Override
                public boolean test(Course c) {
                    return c.getName() == course.getValue();
                }
            });
            exam.setDescription(description.getText());
            exam.setTime_retry(Integer.valueOf(timeRetry.getText()));
            exam.setScoring_type(0);
            exam.setEnd(AppUtils.fromDateAndTime(toDate.getValue(),toTime.getText()));
            exam.setStart(AppUtils.fromDateAndTime(fromDate.getValue(),fromTime.getText()));

            Integer id = examDAO.save(exam);
            for (ExamQuestion examQuestion : examQuestions) {
                examQuestion.setExam_id(id);
                examQuestionDAO.save(examQuestion);
            }
            AppUtils.showInfo(event, "Create test success", "Create test successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });
        }catch (Exception e){
            AppUtils.showAlert(event,"Create test false",e.getMessage());
        }

    }

    public void onImportTestQuestion(ActionEvent event) {
    }
}
