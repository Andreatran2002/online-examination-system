package org.ute.onlineexamination.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.daos.*;
import org.ute.onlineexamination.models.*;
import org.ute.onlineexamination.tableviews.ExamTableView;
import org.ute.onlineexamination.tableviews.StudentUserTableView;
import org.ute.onlineexamination.tableviews.TeacherUserTableView;
import org.ute.onlineexamination.tableviews.CourseTableView;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdminController implements Initializable {

//    public TableView<StudentUser> studentUserTableView;

    public ObservableList<StudentUser> studentUserObservableList;
    public AnchorPane studentUserViewPane;

    public ObservableList<TeacherUser> teacherUserObservableList;
    public AnchorPane teacherUserViewPane;

    public ObservableList<Course>courseObservableList;
    public AnchorPane courseViewPane;

    public ObservableList<Examination>examObservableList;
    public AnchorPane examViewPane;

    public FilteredList<StudentUser> filterStudentUserList;

    public FilteredList<TeacherUser> filterTeacherUserList;

    public FilteredList<Course> filterCourseList;

    public FilteredList<Examination> filterExamList;


    public TableView<StudentUser> studentUserTableView;

    public TableView<TeacherUser> teacherUserTableView;

    public TableView<Course> courseTableView;

    public TableView<Examination> examTableView;

    StudentUser studentUser;

    TeacherUser teacherUser;

    Course course;

    Examination exam;

    UserDAO userDAO;

    StudentUserDAO studentUserDAO;

    TeacherUserDAO teacherUserDAO;

    CourseDAO courseDAO;

    ExamDAO examDAO;

    @FXML
    TextField studentUserNameFilter;

    @FXML
    TextField studentUserMobileFilter;

    @FXML
    TextField teacherUserNameFilter;

    @FXML
    TextField teacherUserMobileFilter;

    @FXML
    TextField courseNameFilter;

    @FXML
    ChoiceBox<String> categoryCourseFilter;

    @FXML
    TextField examNameFilter;

    @FXML
    TextField examScoringType;

    @FXML
    Label labelTotalStudents;

    @FXML
    Label labelTotalTeachers;

    @FXML
    Label labelTotalCourses;

    @FXML
    Label labelTotalExams;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDAO = new UserDAO();
        studentUserDAO = new StudentUserDAO();
        teacherUserDAO = new TeacherUserDAO();
        courseDAO = new CourseDAO();
        examDAO = new ExamDAO();

        studentUserObservableList = FXCollections.observableArrayList();
        teacherUserObservableList = FXCollections.observableArrayList();
        courseObservableList = FXCollections.observableArrayList();
        examObservableList = FXCollections.observableArrayList();

        studentUser = new StudentUser();
        teacherUser = new TeacherUser();
        course = new Course();
        exam = new Examination();


        categoryCourseFilter.setItems(FXCollections.observableArrayList("All","Artificial Intelligence", "Machine Learning", "Cybersecurity", "Digital Marketing", "Photography", "Music Production", "Language Learning", "Personal Development", "Finance", "Health & Fitness"));
        categoryCourseFilter.setValue("All");

        initFilter();
        initStudentUser();
        initTeacherUser();
        initCourse();
        initExam();
        load();
    }

    public AdminController() {

    }

    void initStudentUser() {

        studentUserObservableList.addAll(studentUserDAO.getAll());
        filterStudentUserList = new FilteredList<>(studentUserObservableList);

        studentUserTableView = new StudentUserTableView(filterStudentUserList, new Callback() {
            @Override
            public Object call(Object o) {
                resetStudentUserView();
                return null;
            }
        });
        studentUserTableView.prefHeightProperty().bind(studentUserViewPane.heightProperty());
        studentUserTableView.prefWidthProperty().bind(studentUserViewPane.widthProperty());
        studentUserViewPane.getChildren().add(studentUserTableView);

    }

    void initTeacherUser() {
        teacherUserObservableList.addAll(teacherUserDAO.getAll());
        filterTeacherUserList = new FilteredList<>(teacherUserObservableList);

        teacherUserTableView = new TeacherUserTableView(filterTeacherUserList, new Callback() {
            @Override
            public Object call(Object o) {
                resetTeacherUserView();
                return null;
            }
        });

        teacherUserTableView.prefHeightProperty().bind(teacherUserViewPane.heightProperty());
        teacherUserTableView.prefWidthProperty().bind(teacherUserViewPane.widthProperty());

        teacherUserViewPane.getChildren().add(teacherUserTableView);
    }

    void initCourse(){
        courseObservableList.addAll(courseDAO.getAll());
        filterCourseList = new FilteredList<>(courseObservableList);

        courseTableView = new CourseTableView(filterCourseList, new Callback() {
            @Override
            public Object call(Object o) {
                resetCourseView();
                return null;
            }
        });

        courseTableView.prefHeightProperty().bind(courseViewPane.heightProperty());
        courseTableView.prefWidthProperty().bind(courseViewPane.widthProperty());
        courseViewPane.setTopAnchor(courseTableView, 0.0);
        courseViewPane.setBottomAnchor(courseTableView, 0.0);
        courseViewPane.setLeftAnchor(courseTableView, 0.0);
        courseViewPane.setRightAnchor(courseTableView, 0.0);

        courseViewPane.getChildren().add(courseTableView);
    }

    void initExam(){
        examObservableList.addAll(examDAO.getAll());
        filterExamList = new FilteredList<>(examObservableList);

        examTableView = new ExamTableView(filterExamList, new Callback() {
            @Override
            public Object call(Object o) {
                resetExamView();
                return null;
            }
        });

        examTableView.prefHeightProperty().bind(examViewPane.heightProperty());
        examTableView.prefWidthProperty().bind(examViewPane.widthProperty());

        examViewPane.getChildren().add(examTableView);
    }

    void initFilter() {
        ChangeListener<String> studentUserListener = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filterStudentUserList.setPredicate(
                    new Predicate<StudentUser>() {
                        public boolean test(StudentUser studentUser) {
                            if (studentUser.getMobile() == null) {
                                return studentUser.getFull_name().contains(studentUserNameFilter.getText()) && Objects.equals(studentUserMobileFilter.getText(), "");
                            }
                            return studentUser.getFull_name().contains(studentUserNameFilter.getText()) && studentUser.getMobile().contains(studentUserMobileFilter.getText());

                        }
                    }
            );
        };
        studentUserNameFilter.textProperty().addListener(studentUserListener);
        studentUserMobileFilter.textProperty().addListener(studentUserListener);

        ChangeListener<String> teacherUserListener = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filterTeacherUserList.setPredicate(
                    new Predicate<TeacherUser>() {
                        public boolean test(TeacherUser teacherUser) {
                            if (teacherUser.getMobile() == null) {
                                return teacherUser.getFull_name().contains(teacherUserNameFilter.getText()) && Objects.equals(teacherUserMobileFilter.getText(), "");
                            }
                            return teacherUser.getFull_name().contains(teacherUserNameFilter.getText()) && teacherUser.getMobile().contains(teacherUserMobileFilter.getText());

                        }
                    }
            );
        };
        teacherUserNameFilter.textProperty().addListener(teacherUserListener);
        teacherUserMobileFilter.textProperty().addListener(teacherUserListener);

        ChangeListener<String> courseListener = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filterCourseList.setPredicate(
                    new Predicate<Course>() {
                        public boolean test(Course t) {
                            if (categoryCourseFilter.getValue() == "All") {
                                return t.getName().contains(courseNameFilter.getText());
                            }
                            return t.getName().contains(courseNameFilter.getText()) && categoryCourseFilter.getValue().contains(t.getCategory());
                        }
                    }
            );
        };
        courseNameFilter.textProperty().addListener(courseListener);
        categoryCourseFilter.getSelectionModel().selectedItemProperty().addListener(courseListener);

        ChangeListener<String> examListener = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filterExamList.setPredicate(
                    new Predicate<Examination>() {
                        public boolean test(Examination exam) {
                            if (exam.getScoring_type() == null) {
                                return exam.getName().contains(examNameFilter.getText()) && Objects.equals(examScoringType.getText(), "");
                            }
                            return exam.getName().contains(examNameFilter.getText()) && String.valueOf(exam.getScoring_type()).contains(examScoringType.getText());

                        }
                    }
            );
        };
        examNameFilter.textProperty().addListener(examListener);
        examScoringType.textProperty().addListener(examListener);
    }
//    void resetData() {
//        resetStudentUserView();
//    }

    public void resetStudentUserView() {
        studentUserObservableList = studentUserDAO.getAll();
        filterStudentUserList = new FilteredList<>(studentUserObservableList);
        studentUserTableView.setItems(filterStudentUserList);

    }

    public void resetTeacherUserView() {
        teacherUserObservableList = teacherUserDAO.getAll();
        filterTeacherUserList = new FilteredList<>(teacherUserObservableList);
        teacherUserTableView.setItems(filterTeacherUserList);

    }

    public void resetCourseView() {
        courseObservableList = courseDAO.getAll();
        filterCourseList = new FilteredList<>(courseObservableList);
        courseTableView.setItems(filterCourseList);

    }

    public void resetExamView() {
        examObservableList = examDAO.getAll();
        filterExamList = new FilteredList<>(examObservableList);
        examTableView.setItems(filterExamList);
    }

    public void load(){
        labelTotalStudents.setText(String.valueOf(studentUserDAO.countStudents()));
        labelTotalTeachers.setText(String.valueOf(teacherUserDAO.countTeachers()));
        labelTotalCourses.setText(String.valueOf(courseDAO.countCourses()));
        labelTotalExams.setText(String.valueOf(examDAO.countExaminations()));
    }
    public void logout(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(AppUtils.APP_TITLE);
        Pane panel = FXMLLoader.load(MainApplication.class.getResource("LoginPage.fxml"));
        stage.setScene(new Scene(panel, 600, 400));
        stage.show();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();;
    }
}
