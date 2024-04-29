package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.daos.ExamQuestionDAO;
import org.ute.onlineexamination.daos.QuestionDAO;
import org.ute.onlineexamination.daos.StudentDAO;
import org.ute.onlineexamination.daos.TeacherDAO;
import org.ute.onlineexamination.models.*;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.ExamQuestion;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.Question;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class TeacherController implements Initializable {
    public TableColumn<Course,String> courseNameColumn;
    public TableColumn<Course,String> courseDescriptionColumn;
    public TableColumn<Course,String> courseCategoryColumn;
    public TableColumn<Course, Timestamp> courseFromColumn;
    public TableColumn<Course,Timestamp> courseToColumn;
    public TableColumn courseActionColumn;
    public TableColumn<Question, String> questionContentColumn;
    public TableColumn  questionCourseColumn;
    public TableColumn<Question, Boolean>  questionActiveColumn;
    public TableColumn questionActionColumn;
    public TableView<Question> questionView;
    public TableView<Examination> examTableView;
    public TableColumn<Examination, String> examTitleColumn;
    public TableColumn examCourseColumn;
    public TableColumn<Examination, String>  examDescriptionColumn;
    public TableColumn examTotalQuestionColumn;
    public TableColumn examActionColumn;
    public TableColumn examStartColumn;
    public TableColumn examEndColumn;
    CourseDAO courseDAO;
    public TableView<Course> courseView;
    public Label labelEmailAddress;
    public Label labelFullName;
    public Label labelPhoneNumber;
    public Label labelTitle;
    public Label labelAddress;

    private ObservableList<Course> courseList;
    private ObservableList<Question> questionList;
    private ObservableList<Examination> examList;
    QuestionDAO questionDAO;
    User user;
    TeacherDAO teacherDAO;
    Teacher teacher;

    public TeacherController() {
        teacherDAO = new TeacherDAO();
    }
    ExamDAO examDAO;
    ExamQuestionDAO examQuestionDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseDAO = new CourseDAO();
        questionDAO = new QuestionDAO();
        examDAO = new ExamDAO();
        examQuestionDAO = new ExamQuestionDAO();
        currentUserName.setText(AppUtils.CURRENT_USER.getFull_name());
        courseList =  FXCollections.observableArrayList();
        questionList =   FXCollections.observableArrayList();
        examList =   FXCollections.observableArrayList();

        initCourseView();
        initQuestionView();
        initExamView();
    }

    void initCourseView(){
        // Course
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("name")) ;
        courseCategoryColumn. setCellValueFactory (new PropertyValueFactory<Course, String > ("category")) ;
        courseFromColumn.setCellValueFactory (new PropertyValueFactory<Course, Timestamp> ("start")) ;
        courseToColumn.setCellValueFactory (new PropertyValueFactory<Course, Timestamp> ("end")) ;
        courseDescriptionColumn.setCellValueFactory (new PropertyValueFactory<Course, String> ("description")) ;
        Callback<TableColumn<Course, String>, TableCell<Course, String>> cellFactory = //
                new Callback<TableColumn<Course, String>, TableCell<Course, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Course, String> param) {
                        final TableCell<Course, String> cell = new TableCell<Course, String>() {
                            final Button editBtn = new Button("Edit");
                            final Button deleteBtn = new Button("Delete");
                            final Button detailBtn = new Button("Detail");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    detailBtn.setOnAction(event -> {
                                        Course course = getTableView().getItems().get(getIndex());
                                        //TODO : Open Detail course page
                                        System.out.println(course.getId()
                                                + "   " + course.getName());
                                    });
                                    editBtn.setOnAction(event -> {
                                        Course course = getTableView().getItems().get(getIndex());
                                        try {
                                            navToUpdateCourse(course);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                    deleteBtn.setOnAction(event -> {
                                        Course course = getTableView().getItems().get(getIndex());
                                        AppUtils.showYesNoOption(event, "Delete course " + course.getName(), "Are you sure to delte this course?", new AlertActionInterface() {
                                            @Override
                                            public void action() {
                                                try{
                                                    courseDAO.delete(course);
                                                    AppUtils.showInfo(event, "Delete course", "Delete course " + course.getName() + " successfull", new AlertActionInterface() {
                                                        @Override
                                                        public void action() {
                                                            resetCourseView();
                                                        }
                                                    });
                                                }catch (Exception e){
                                                    AppUtils.showAlert(event, "Delete course", "Delete course " + course.getName() + " false");
                                                }
                                            }
                                        });
                                    });
                                    HBox hbox = new HBox(detailBtn,editBtn,deleteBtn);
                                    setGraphic(hbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        courseActionColumn.setCellFactory(cellFactory);
        resetCourseView();
    }

    void initQuestionView(){
        // Question
        questionContentColumn.setCellValueFactory(new PropertyValueFactory<Question, String>("content")) ;
        questionActiveColumn.setCellValueFactory(new PropertyValueFactory<Question, Boolean>("active")) ;
        Callback<TableColumn<Question, String>, TableCell<Question, String>> questionActionCell = //
                new Callback<TableColumn<Question, String>, TableCell<Question, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Question, String> param) {
                        final TableCell<Question, String> cell = new TableCell<Question, String>() {
                            final Button editBtn = new Button("Edit");
                            final Button deleteBtn = new Button("Delete");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    editBtn.setOnAction(event -> {
                                        Question question = getTableView().getItems().get(getIndex());
                                        try {
                                            navToUpdateQuestion(question);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                    deleteBtn.setOnAction(event -> {
                                        Question question = getTableView().getItems().get(getIndex());
                                        AppUtils.showYesNoOption(event, "Delete question ",  "Are you sure to delete this question?", new AlertActionInterface() {
                                            @Override
                                            public void action() {
                                                try{
                                                    questionDAO.delete(question);
                                                    AppUtils.showInfo(event, "Delete question", "Delete question successfull", new AlertActionInterface() {
                                                        @Override
                                                        public void action() {
                                                            resetCourseView();
                                                        }
                                                    });
                                                }catch (Exception e){
                                                    AppUtils.showAlert(event, "Delete question", "Delete question false");
                                                }
                                            }
                                        });
                                    });
                                    HBox hbox = new HBox(editBtn,deleteBtn);
                                    setGraphic(hbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        Callback<TableColumn<Question, String>, TableCell<Question, String>> questionCourseCell = //
                new Callback<TableColumn<Question, String>, TableCell<Question, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Question, String> param) {
                        final TableCell<Question, String> cell = new TableCell<Question, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Question q = getTableView().getItems().get(getIndex());
                                    FilteredList<Course> courseSelected = courseList.filtered(new Predicate<Course>() {
                                        @Override
                                        public boolean test(Course c) {
                                            return c.getId() == q.getCourse_id();
                                        }
                                    });
                                    setText(courseSelected.getFirst().getName());
                                }
                            }
                        };
                        return cell;
                    }
                };

        questionActionColumn.setCellFactory(questionActionCell);
        questionCourseColumn.setCellFactory(questionCourseCell) ;
        resetQuestionView();

        loadUser();
    }

    void initExamView(){
        // Exam
        examTitleColumn.setCellValueFactory(new PropertyValueFactory<Examination, String>("name")) ;
        examDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Examination, String>("description")) ;
        examStartColumn.setCellValueFactory(new PropertyValueFactory<Examination, String>("start")) ;
        examEndColumn.setCellValueFactory(new PropertyValueFactory<Examination, String>("end")) ;

        Callback<TableColumn<Examination, String>, TableCell<Examination, String>> examActionCell = //
                new Callback<TableColumn<Examination, String>, TableCell<Examination, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Examination, String> param) {
                        final TableCell<Examination, String> cell = new TableCell<Examination, String>() {
                            final Button editBtn = new Button("Edit");
                            final Button deleteBtn = new Button("Delete");
                            final Button detailBtn = new Button("Detail");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    detailBtn.setOnAction(event -> {
                                        Examination exam = getTableView().getItems().get(getIndex());
                                        //TODO : Open Detail course page
                                    });
                                    editBtn.setOnAction(event -> {
                                        Examination exam = getTableView().getItems().get(getIndex());
//                                        try {
//                                            navToUpdateExam(exam);
//                                        } catch (IOException e) {
//                                            throw new RuntimeException(e);
//                                        }
                                    });
                                    deleteBtn.setOnAction(event -> {
                                        Examination exam = getTableView().getItems().get(getIndex());
                                        AppUtils.showYesNoOption(event, "Delete examination " + exam.getName(), "Are you sure to delete this exam?", new AlertActionInterface() {
                                            @Override
                                            public void action() {
                                                try{
//                                                    examDAO.delete(exam);
                                                    AppUtils.showInfo(event, "Delete exam", "Delete exam " + exam.getName() + " successfull", new AlertActionInterface() {
                                                        @Override
                                                        public void action() {
                                                            resetCourseView();
                                                        }
                                                    });
                                                }catch (Exception e){
                                                    AppUtils.showAlert(event, "Delete exam", "Delete exam " + exam.getName() + " false");
                                                }
                                            }
                                        });
                                    });
                                    HBox hbox = new HBox(detailBtn,editBtn,deleteBtn);
                                    setGraphic(hbox);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<Examination, String>, TableCell<Examination, String>> examCourseCell = //
                new Callback<TableColumn<Examination, String>, TableCell<Examination, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Examination, String> param) {
                        final TableCell<Examination, String> cell = new TableCell<Examination, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Examination q = getTableView().getItems().get(getIndex());
                                    FilteredList<Course> courseSelected = courseList.filtered(new Predicate<Course>() {
                                        @Override
                                        public boolean test(Course c) {
                                            return c.getId() == q.getCourse_id();
                                        }
                                    });
                                    setText(courseSelected.getFirst().getName());
                                }
                            }
                        };
                        return cell;
                    }
                };
        Callback<TableColumn<Examination, String>, TableCell<Examination, String>> examTotalQuestionCell = //
                new Callback<TableColumn<Examination, String>, TableCell<Examination, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Examination, String> param) {
                        final TableCell<Examination, String> cell = new TableCell<Examination, String>() {
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Examination q = getTableView().getItems().get(getIndex());
                                    ObservableList<ExamQuestion> totalQuestion = examQuestionDAO.getByExamId(q.getId());
                                    setText(String.valueOf(totalQuestion.size()));
                                }
                            }
                        };
                        return cell;
                    }
                };
        examActionColumn.setCellFactory(examActionCell);
        examCourseColumn.setCellFactory(examCourseCell);
        examTotalQuestionColumn.setCellFactory(examTotalQuestionCell);
        resetExamView();

    }

    public void resetCourseView(){
        courseList = courseDAO.getByTeacherId(AppUtils.CURRENT_ROLE.id);
        courseView.setItems(courseList);
    }

    public void resetExamView(){
        examList = examDAO.getByTeacherId(AppUtils.CURRENT_ROLE.id);
        examTableView.setItems(examList);
    }

    public void resetQuestionView(){
        questionList = questionDAO.getByTeacherId(AppUtils.CURRENT_ROLE.id);
        questionView.setItems(questionList);
    }

    public Label currentUserName;

    public void navToNewCourse(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("NewCoursePage.fxml"));
        stage.setScene(new Scene(panel, 600, 400));
        stage.show();
    }
    void navToUpdateCourse(Course course) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("UpdateCoursePage.fxml"));
        UpdateCourseController controller = new UpdateCourseController();
        controller.setCourse(course);
        loader.setController(controller);
        Pane panel = loader.load();
        stage.setScene(new Scene(panel, 600, 400));
        stage.show();
    }
    void navToUpdateQuestion(Question question) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("UpdateQuestionPage.fxml"));
        UpdateQuestionController controller = new UpdateQuestionController();
        controller.setQuestion(question);
        loader.setController(controller);
        Pane panel = loader.load();
        stage.setScene(new Scene(panel, 600, 500));
        stage.show();
    }

    public void navToNewExam(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("NewTestPage.fxml"));
        stage.setScene(new Scene(panel, 600, 800));
        stage.show();
    }

    public void onReloadCourse(ActionEvent event) {
        resetCourseView();
    }

    public void navToNewQuestion(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("NewQuestionPage.fxml"));
        stage.setScene(new Scene(panel, 600, 500));
        stage.show();
    }
    public void loadUser(){
        user = AppUtils.CURRENT_USER;
        labelEmailAddress.setText(user.getEmail());
        labelFullName.setText(user.getFull_name());
        labelPhoneNumber.setText(user.getMobile());
        teacher = teacherDAO.getByUserId(user.getId());
        labelTitle.setText(teacher.getTitle());
        labelAddress.setText(teacher.getAddress());
    }
    public void navToChangePasswordPage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Change Password");
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("ChangePasswordPage.fxml"));
        stage.setScene(new Scene(panel, 400, 400));
        stage.show();
    }
    public void navToUpdateTeacherPage(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Update Teacher");
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("UpdateTeacherPage.fxml"));
        stage.setScene(new Scene(panel, 500, 500));
        stage.show();
    }

}
