package org.ute.onlineexamination.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class TeacherController implements Initializable {
    public TableColumn<Course,String> courseNameColumn;
    public TableColumn<Course,String> courseDescriptionColumn;
    public TableColumn<Course,String> courseCategoryColumn;
    public TableColumn<Course, Timestamp> courseFromColumn;
    public TableColumn<Course,Timestamp> courseToColumn;
    public TableColumn courseActionColumn;
    CourseDAO courseDAO;
    public TableView<Course> courseView;

    private ObservableList<Course> courseList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseDAO = new CourseDAO();
        currentUserName.setText(AppUtils.CURRENT_USER.getFull_name());
        courseList =  FXCollections.observableArrayList();
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
                                                            resetDataView();
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
        resetDataView();
    }

    public void resetDataView(){
        courseList = courseDAO.getByTeacherId(AppUtils.CURRENT_ROLE.id);
        courseView.setItems(courseList);
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

    public void navToNewExam(ActionEvent event) {
    }

    public void onReloadCourse(ActionEvent event) {
        resetDataView();
    }
}
