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
import org.ute.onlineexamination.models.Question;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class UpdateQuestionController implements Initializable {
    public CheckBox active;
    public ChoiceBox course;
    public TextField content;
    public TextField option;
    public TableView<Answer> answerView;
    public TableColumn<Answer,String>  answerColumn;
    public TableColumn<Answer,Boolean>  correctColumn;
    public TableColumn actionColumn;
    private ObservableList<Answer> answerList;
    QuestionDAO questionDAO;
    CourseDAO courseDAO;
    ObservableList<Course> coursesByTeacher;
    AnswerDAO answerDAO;
    Question question ;

    public Question getQuestion() {
        return question;
    }

    String getCourseName(Integer id){
        FilteredList<Course> courses = coursesByTeacher.filtered(new Predicate<Course>() {
            @Override
            public boolean test(Course course) {
                return Objects.equals(course.getId(), id);
            }
        });
        return courses.getFirst().getName();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        questionDAO = new QuestionDAO();
        answerDAO = new AnswerDAO();
        courseDAO = new CourseDAO();
        coursesByTeacher= courseDAO.getByTeacherId(AppUtils.CURRENT_ROLE.id);
        ObservableList<String> ts =  FXCollections.observableArrayList();
        for (int i = 0; i < coursesByTeacher.size(); i++) {
            ts.add(coursesByTeacher.get(i).getName());
        }
        course.setItems(ts);
        answerList =  FXCollections.observableArrayList();
        correctColumn. setCellValueFactory (new PropertyValueFactory<Answer, Boolean > ("correct")) ;
        answerColumn.setCellValueFactory (new PropertyValueFactory<Answer, String> ("content")) ;
        Callback<TableColumn<Answer, String>, TableCell<Answer, String>> actionCell = //
                new Callback<TableColumn<Answer, String>, TableCell<Answer, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Answer, String> param) {
                        final TableCell<Answer, String> cell = new TableCell<Answer, String>() {
                            final Button deleteBtn = new Button("Delete");
                            final Button correctBtn = new Button("Correct/Incorrect");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    deleteBtn.setOnAction(event -> {
                                        answerList.remove(getIndex());
                                    });
                                    correctBtn.setOnAction(event -> {
                                        Answer answer = getTableView().getItems().get(getIndex());
                                        answer.setCorrect(!answer.getCorrect());
                                        getTableView().getItems().set(getIndex(), answer);
                                        resetDataView();
                                    });
                                    HBox hBox = new HBox(deleteBtn,correctBtn);
                                    setGraphic(hBox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        actionColumn.setCellFactory(actionCell);
        // Set values :
        course.setValue(getCourseName(question.getCourse_id()));
        content.setText(question.getContent());
        active.setText(String.valueOf(question.getActive()));
        answerList = question.getAnswers();
        resetDataView();
    }

    boolean checkValidData(){
        // TODO: From date before to date

        // TODO: not empty name , categroy , description

        return true;
    }

    public void onUpdateQuestion(ActionEvent event) {
        Boolean isValid = checkValidData();
        if (!isValid) {
            return ;
        }
        try {
            Question question = new Question();
            question.setContent(content.getText());
            FilteredList<Course> courseSelected = coursesByTeacher.filtered(new Predicate<Course>() {
                @Override
                public boolean test(Course c) {
                    return c.getName() == course.getValue();
                }
            });
            question.setCourse_id(courseSelected.getFirst().getId());
            question.setActive(active.isSelected());
            questionDAO.update(question);
            updateAnswers();
            AppUtils.showInfo(event, "Update question success", "Update question successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });
        }catch (Exception e){
            AppUtils.showAlert(event,"Update question false",e.getMessage());
        }
    }
    void updateAnswers(){
        for (Answer answer : answerList) {
            boolean found = false;
            for (Answer a : question.getAnswers()) {
                if (answer.getId() == -1){
                    continue;
                }
                if (Objects.equals(answer.getId(), a.getId())) {
                    found = true;
                    answerDAO.update(answer);
                    break;
                }
            }
            if (!found) {
                answer.setQuestion_id(question.getId());
                answerDAO.save(answer);
            }
        }

        for (Answer answer : question.getAnswers()) {
            boolean found = false;
            for (Answer a : answerList) {
                if (a.getId() == -1){
                    continue;
                }
                if (Objects.equals(answer.getId(), a.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                answerDAO.delete(answer);
            }
        }
    }

    public void resetDataView(){
        answerView.setItems(answerList);
    }


    public void onAddAnswerOption(ActionEvent event) {
        if (option.getText().isEmpty()){
            AppUtils.showAlert(event,"Wrong input","Option can't not be empty");
            return ;
        }
        answerList.add(new Answer(option.getText(), false,true));
        option.setText("");
        resetDataView();
    }
}
