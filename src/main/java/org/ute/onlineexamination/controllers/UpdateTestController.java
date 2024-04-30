package org.ute.onlineexamination.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.daos.ExamQuestionDAO;
import org.ute.onlineexamination.daos.QuestionDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.ExamQuestion;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.Question;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class UpdateTestController implements Initializable {

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
    public TableColumn<Question, String> questionColumn;
    public TableColumn priorityColumn;
    public TableColumn actionColumn;
    public TableView questionView;
    public ChoiceBox<String> questionOptions;
    public TextField totalMinutes;
    ExamDAO examDAO;
    ExamQuestionDAO examQuestionDAO;
    QuestionDAO questionDAO;

    CourseDAO courseDAO;
    ObservableList<Course> coursesByTeacher;
    ObservableList<Question> questionByCourse;
    ObservableList<Question> examQuestions;
    ToggleGroup selectScoringType;
    Examination examination ;
    public UpdateTestController(Examination examination){
        this.examination = examination;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        examDAO = new ExamDAO();
        courseDAO = new CourseDAO();
        questionDAO = new QuestionDAO();
        examQuestionDAO = new ExamQuestionDAO();
        examQuestions = FXCollections.observableArrayList();

        resetData();
        initView();
    }

    void resetData(){
        coursesByTeacher= courseDAO.getByTeacherId(AppUtils.CURRENT_ROLE.id);
//        examQuestions = examQuestionDAO.getByExamId(examination.getId());
    }

    void initView(){
        ObservableList<String> cOption =  FXCollections.observableArrayList();
        for (int i = 0; i < coursesByTeacher.size(); i++) {
            cOption.add(coursesByTeacher.get(i).getName() );
        }
        course.setItems(cOption);
        selectScoringType = new ToggleGroup();
        scoringHighest.setToggleGroup(selectScoringType);
        scoringAverage.setToggleGroup(selectScoringType);
        course.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(course.getValue());
                if (course.getValue() == null){
                    return;
                }
                FilteredList<Course> courseSelected = coursesByTeacher.filtered(new Predicate<Course>() {
                    @Override
                    public boolean test(Course c) {
                        return c.getName() == course.getValue();
                    }
                });
                getQuestionData(courseSelected.getFirst().getId());
            }
        });

        questionColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("content")) ;
        Callback<TableColumn<Question, String>, TableCell<Question, String>> priorityCell = //
                new Callback<TableColumn<Question, String>, TableCell<Question, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Question, String> param) {
                        final TableCell<Question, String> cell = new TableCell<Question, String>() {
                            final Spinner<Integer> priority = new Spinner<Integer>(1,10,getTableView().getItems().get(getIndex()).getPriority());

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    int currentIndex = getIndex();
                                    priority.setEditable(true );
                                    priority.valueProperty().addListener(new ChangeListener<Integer>() {
                                        @Override
                                        public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                                            Question question = getTableView().getItems().get(currentIndex);
                                            question.setPriority(newValue);
                                            getTableView().getItems().set(getIndex(), question);
                                        }
                                    });

                                    HBox hbox = new HBox(priority);
                                    setGraphic(hbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<Question, String>, TableCell<Question, String>> actionCell = //
                new Callback<TableColumn<Question, String>, TableCell<Question, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Question, String> param) {
                        final TableCell<Question, String> cell = new TableCell<Question, String>() {
                            final Button deleteBtn = new Button("Delete");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            examQuestions.remove(getIndex());
                                        }
                                    });
                                    HBox hbox = new HBox(deleteBtn);
                                    setGraphic(hbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        priorityColumn.setCellFactory(priorityCell);
        actionColumn.setCellFactory(actionCell);
    }

    void getQuestionData(Integer course_id){
        //TODO: thay doi cac options trong question Options
        questionByCourse = questionDAO.getByCourseId(course_id);
        ObservableList<String> qOption =  FXCollections.observableArrayList();
        for (int i = 0; i < questionByCourse.size(); i++) {
            qOption.add(questionByCourse.get(i).getContent() );
        }
        questionOptions.setItems(qOption);
        //TODO: Neu các question đã chọn mà không thuộc đúng course thì tự động xoá ra khỏi quếtions list
    }


    boolean checkValidData(){
        // TODO: From date before to date

        // TODO: not empty name , categroy , description

        return true;
    }

    void resetQuestionTableView(){
        questionView.setItems(examQuestions);
    }
    public void onUpdateTest(ActionEvent event) {
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
            exam.setCourse_id(courseSelected.getFirst().getId());
            exam.setDescription(description.getText());
            exam.setTime_retry(Integer.valueOf(timeRetry.getText()));
            exam.setScoring_type(scoringHighest.isSelected()? 1 : 2);
            exam.setEnd(AppUtils.fromDateAndTime(toDate.getValue(),toTime.getText()));
            exam.setStart(AppUtils.fromDateAndTime(fromDate.getValue(),fromTime.getText()));
            exam.setTotal_minutes(Integer.valueOf(totalMinutes.getText()));
            Integer id = examDAO.save(exam);
            for (Question question : examQuestions) {
                ExamQuestion examQuestion = new ExamQuestion(question.getId(), id);
                examQuestion.setPriority(question.getPriority());
                examQuestionDAO.save(examQuestion);
            }
            AppUtils.showInfo(event, "Update test success", "Update test successfull", new AlertActionInterface() {
                @Override
                public void action() {
                    Stage appStage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
                    appStage.close();
                }
            });
        }catch (Exception e){
            AppUtils.showAlert(event,"Update test false",e.getMessage());
        }

    }

    public void onImportTestQuestion(ActionEvent event) {
    }

    public void updateQuestion(ActionEvent event) {
        FilteredList<Question> questionSelected = questionByCourse.filtered(new Predicate<Question>() {
            @Override
            public boolean test(Question c) {
                return Objects.equals(c.getContent(), questionOptions.getValue());
            }
        });
        if (questionSelected.isEmpty()){
            return;
        }
        Question question = new Question();
        question.setContent(questionOptions.getValue());
        question.setId( questionSelected.getFirst().getId());
        for (int i = 0; i <examQuestions.size(); i++) {
            if (examQuestions.get(i).getId()==question.getId()){
                return;
            }
        }

        examQuestions.add(question);
        resetQuestionTableView();
    }
}
