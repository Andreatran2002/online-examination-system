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
import org.ute.onlineexamination.daos.AnswerDAO;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.daos.QuestionDAO;
import org.ute.onlineexamination.models.Answer;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.Question;
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
    public ChoiceBox scoringType;

    CourseDAO courseDAO;
    ObservableList<Course> coursesByTeacher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseDAO = new CourseDAO();
        coursesByTeacher= courseDAO.getByTeacherId(AppUtils.CURRENT_ROLE.id);
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

            Integer id = questionDAO.save(question);
            for (Answer answerOption : answerList) {
                answerOption.setQuestion_id(id);
                answerDAO.save(answerOption);
            }
            AppUtils.showInfo(event, "Create question success", "Create question successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });
        }catch (Exception e){
            AppUtils.showAlert(event,"Create question false",e.getMessage());
        }

    }

    public void onImportTestQuestion(ActionEvent event) {
    }
}
