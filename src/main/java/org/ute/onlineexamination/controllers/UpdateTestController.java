package org.ute.onlineexamination.controllers;

import javafx.beans.binding.Bindings;
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
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
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

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
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
        questionByCourse = questionDAO.getByCourseId(examination.getCourse_id());
        examQuestions = questionDAO.getByExamId(examination.getId());
        for (int i = 0; i < examQuestions.size(); i++) {
            System.out.println(examQuestions.get(i).getContent());
            System.out.println(examQuestions.get(i).getPriority());
        }
    }

    void initView(){
        ObservableList<String> cOption =  FXCollections.observableArrayList();
        for (int i = 0; i < coursesByTeacher.size(); i++) {
            cOption.add(coursesByTeacher.get(i).getName() );
        }
        course.setItems(cOption);

        course.setValue(examination.course.getName());
        title.setText(examination.getName());
        description.setText(examination.getDescription());
        fromDate.setValue(examination.getStart().toLocalDateTime().toLocalDate());
        toDate.setValue(examination.getEnd().toLocalDateTime().toLocalDate());
        fromTime.setText(examination.getStart().toLocalDateTime().format(DateTimeFormatter.ofPattern("hh:mm")));
        toTime.setText(examination.getEnd().toLocalDateTime().format(DateTimeFormatter.ofPattern("hh:mm")));
        totalMinutes.setText(String.valueOf(examination.getTotal_minutes()));

        timeRetry.setText(String.valueOf(examination.getTimes_retry()));
        if (examination.getScoring_type()==1){
            scoringHighest.setSelected(true);
        }
        else{
            scoringAverage.setSelected(true);
        }
        //
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
        Callback<TableColumn<Question, Integer>, TableCell<Question, Integer>> priorityCell = new Callback<TableColumn<Question, Integer>, TableCell<Question, Integer>>() {
                    @Override
                    public TableCell call(final TableColumn<Question, Integer> param) {
                        final TableCell<Question, String> cell = new TableCell<Question, String>() {
                            final Spinner<Integer> priority = new Spinner<Integer>(1,10,1);

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

                                    setGraphic(priority);
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
                                            Question q = examQuestions.get(getIndex());
                                            examQuestions.remove(getIndex());
                                            ExamQuestion currentExamQuestion = examQuestionDAO.getByQuestionId(q.getId(), examination.getId()).get();
                                            if (currentExamQuestion.getId()!=null){
                                                examQuestionDAO.delete(currentExamQuestion);
                                            }
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
        getQuestionData(examination.getCourse_id());
        questionView.setItems(examQuestions);

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
            examination.setName(title.getText());
            FilteredList<Course> courseSelected = coursesByTeacher.filtered(new Predicate<Course>() {
                @Override
                public boolean test(Course c) {
                    return c.getName() == course.getValue();
                }
            });
            examination.setCourse_id(courseSelected.getFirst().getId());
            examination.setDescription(description.getText());
            examination.setTimes_retry(Integer.valueOf(timeRetry.getText()));
            examination.setScoring_type(scoringHighest.isSelected()? 1 : 2);
            examination.setEnd(AppUtils.fromDateAndTime(toDate.getValue(),toTime.getText()));
            examination.setStart(AppUtils.fromDateAndTime(fromDate.getValue(),fromTime.getText()));
            examination.setTotal_minutes(Integer.valueOf(totalMinutes.getText()));
            examDAO.update(examination);
            for (Question question : examQuestions) {
                examQuestionDAO.saveOrUpdateQuestion(question, examination.getId());
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

    public void addQuestion(ActionEvent event) {
        FilteredList<Question> questionSelected = questionByCourse.filtered(new Predicate<Question>() {
            @Override
            public boolean test(Question c) {
                return Objects.equals(c.getContent(), questionOptions.getValue());
            }
        });
        if (questionSelected.isEmpty()){
            return;
        }
        Question examQuestion = new Question();
        examQuestion.setContent(questionOptions.getValue());
        examQuestion.setId( questionSelected.getFirst().getId());
        for (int i = 0; i <examQuestions.size(); i++) {
            if (Objects.equals(examQuestions.get(i).getId(), examQuestion.getId())){
                return;
            }
        }

        examQuestions.add(examQuestion);
//        resetQuestionTableView();
    }

}
