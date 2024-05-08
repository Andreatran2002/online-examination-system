package org.ute.onlineexamination.tableviews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.ute.onlineexamination.models.StudentUser;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;
import org.ute.onlineexamination.daos.StudentUserDAO;


import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class StudentUserTableView extends TableView<StudentUser> {
    Callback reloadcallback;
    public StudentUserTableView(ObservableList<StudentUser> studentUsers, Callback reloadcallback) {
        super(studentUsers);
        this.reloadcallback = reloadcallback;



        // Create TableColumn instances for each attribute
        TableColumn<StudentUser, Integer> studentId = this.createColumn("Student Id", "id");
        TableColumn<StudentUser, Integer> userId = this.createColumn("User Id", "user_id");
        TableColumn<StudentUser, String> fullName = this.createColumn("Tên đầy đủ", "full_name");
        TableColumn<StudentUser, String> mobile = this.createColumn("Số điện thoại", "mobile");
        TableColumn<StudentUser, String> email = this.createColumn("Email", "email");
        TableColumn<StudentUser, Timestamp> last_login = this.createColumn("Lần đăng nhập gần nhất", "last_login");
        TableColumn<StudentUser, Timestamp> created_at = this.createColumn("Ngày tạo", "created_at");
        TableColumn<StudentUser, Timestamp> updated_at = this.createColumn("Ngày cập nhật", "updated_at");
        TableColumn<StudentUser, Timestamp> deleted_at = this.createColumn("Ngày xoá", "deleted_at");
        TableColumn action = new TableColumn<>("Action");
        StudentUserDAO studentUserDAO = new StudentUserDAO();
        action.setCellFactory(param -> new TableCell<StudentUser, Void>() {

            final Button btnDelete = new Button("Delete");
            final Button btnRetore = new Button("Restore");

            {
                btnDelete.setOnAction(event -> {
                    StudentUser studentUser = getTableView().getItems().get(getIndex());
                    AppUtils.showYesNoOption(event, "Delete student " + studentUser.getFull_name(), "Are you sure to delete this student?", new AlertActionInterface() {

                        @Override
                        public void action() throws IOException {
                            try {
                                studentUserDAO.delete(studentUser);
                                AppUtils.showInfo(event, "Delete student", "Delete student " + studentUser.getFull_name() + " successfull", new AlertActionInterface() {
                                    @Override
                                    public void action() {
                                        reloadcallback.call(true);
                                    }
                                });
                            } catch (Exception e) {
                                AppUtils.showAlert(event, "Delete student", "Delete student " + studentUser.getFull_name() + " false");
                            }
                        }
                    });
                });
            }

            {
                btnRetore.setOnAction(event -> {
                    StudentUser studentUser = getTableView().getItems().get(getIndex());
                    AppUtils.showYesNoOption(event, "Restore student " + studentUser.getFull_name(), "Are you sure to restore this student?", new AlertActionInterface() {

                        @Override
                        public void action() throws IOException {
                            try {
                                studentUserDAO.restore(studentUser);
                                AppUtils.showInfo(event, "Restore student", "Restore student " + studentUser.getFull_name() + " successfull", new AlertActionInterface() {
                                    @Override
                                    public void action() {
                                        reloadcallback.call(true);
                                    }
                                });
                            } catch (Exception e) {
                                AppUtils.showAlert(event, "Restore student", "Restore student " + studentUser.getFull_name() + " false");
                            }
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    StudentUser studentUser = getTableView().getItems().get(getIndex());
                    if (studentUser.getDeleted_at() == null) {
                        btnRetore.setDisable(false);
                        setGraphic(btnDelete);// Không vô hiệu hóa nút
                    } else {
                        // Vô hiệu hóa nút
                        btnDelete.setDisable(false);
                        setGraphic(btnRetore);
                    }


                }
            }
        });


        this.getColumns().addAll(studentId, userId, fullName, mobile, email, last_login, created_at, updated_at, deleted_at, action);

    }

    private <T> TableColumn<StudentUser, T> createColumn(String title, String property) {
        TableColumn<StudentUser, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }

    private ObservableList<StudentUser> studentUsersList;
    private FilteredList<StudentUser> filterStudentUserList;

//    public TextField studentUserFilterName = new TextField("");
//    public TextField studentUserFilterMobile = new TextField("");

    @FXML
    TextField studentUserNameFilter;
    @FXML
    TextField studentUserMobileFilter;

    public TableView<StudentUser> studentUserTableView;

    StudentUserDAO studentUserDAO = new StudentUserDAO();

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
////        initFilter();
////        resetData();
//        studentUserNameFilter.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//                System.out.println(observableValue);
//            }
//        });
//    }


//    void initFilter() {
//        ChangeListener<String> studentUserListener = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
//            filterStudentUserList.setPredicate(
//                    new Predicate<StudentUser>() {
//                        public boolean test(StudentUser studentUser) {
//                            return studentUser.getFull_name().contains(studentUserNameFilter.getText()) && studentUser.getMobile().contains(studentUserMobileFilter.getText());
//
//                        }
//                    }
//            );
//        };
//        studentUserNameFilter.textProperty().addListener(studentUserListener);
//        studentUserMobileFilter.textProperty().addListener(studentUserListener);
//    }
//
//    void resetData() {
//        resetStudentUserView();
//    }
//
//    public void resetStudentUserView() {
//        studentUsersList = studentUserDAO.getAll();
//        filterStudentUserList = new FilteredList<>(studentUsersList);
//        setItems(filterStudentUserList);
//
//    }
}
