package org.ute.onlineexamination.tableviews;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.ute.onlineexamination.daos.StudentUserDAO;
import org.ute.onlineexamination.daos.TeacherDAO;
import org.ute.onlineexamination.daos.TeacherUserDAO;
import org.ute.onlineexamination.models.StudentUser;
import org.ute.onlineexamination.models.TeacherUser;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class TeacherUserTableView extends TableView<TeacherUser>{

    Callback reloadcallback;
    public TeacherUserTableView(ObservableList<TeacherUser> teacherUsers, Callback reloadcallback) {

        super(teacherUsers);
        this.reloadcallback = reloadcallback;

        // Create TableColumn instances for each attribute
        TableColumn<TeacherUser, Integer> teacherId = this.createColumn("TeacherId", "id");
        teacherId.setPrefWidth(56);
        TableColumn<TeacherUser, Integer> userId = this.createColumn("User Id", "user_id");
        userId.setPrefWidth(56);
        TableColumn<TeacherUser, String> fullName = this.createColumn("Tên đầy đủ", "full_name");
        fullName.setPrefWidth(136);
        TableColumn<TeacherUser, String> mobile = this.createColumn("Số điện thoại", "mobile");
        mobile.setPrefWidth(76);
        TableColumn<TeacherUser, String> email = this.createColumn("Email", "email");
        email.setPrefWidth(76);
        TableColumn<TeacherUser, String> title = this.createColumn("Title", "title");
        title.setPrefWidth(76);
        TableColumn<TeacherUser, String> address = this.createColumn("Address", "address");
        title.setPrefWidth(136);
        TableColumn<TeacherUser, Timestamp> last_login = this.createColumn("Lần đăng nhập gần nhất", "last_login");
        last_login.setPrefWidth(78.5);
        TableColumn<TeacherUser, Timestamp> created_at = this.createColumn("Ngày tạo", "created_at");
        created_at.setPrefWidth(78.5);
        TableColumn<TeacherUser, Timestamp> updated_at = this.createColumn("Ngày cập nhật", "updated_at");
        updated_at.setPrefWidth(78.5);
        TableColumn<TeacherUser, Timestamp> deleted_at = this.createColumn("Ngày xoá", "deleted_at");
        deleted_at.setPrefWidth(78.5);
        TableColumn action = new TableColumn<>("Action");
        action.setPrefWidth(66);
        TeacherUserDAO teacherUserDAO = new TeacherUserDAO();
        action.setCellFactory(param -> new TableCell<TeacherUser, Void>() {

            final Button btnDelete = new Button("Delete");
            final Button btnRetore = new Button("Restore");

            {
                btnDelete.setOnAction(event -> {
                    TeacherUser teacherUser = getTableView().getItems().get(getIndex());
                    AppUtils.showYesNoOption(event, "Delete course " + teacherUser.getFull_name(), "Are you sure to delete this course?", new AlertActionInterface() {

                        @Override
                        public void action() throws IOException {
                            try {
                                teacherUserDAO.delete(teacherUser);
                                AppUtils.showInfo(event, "Delete course", "Delete course " + teacherUser.getFull_name() + " successfull", new AlertActionInterface() {
                                    @Override
                                    public void action() {
                                        reloadcallback.call(true);
                                    }
                                });
                            } catch (Exception e) {
                                AppUtils.showAlert(event, "Delete course", "Delete course " + teacherUser.getFull_name() + " false");
                            }
                        }
                    });
                });
            }

            {
                btnRetore.setOnAction(event -> {
                    TeacherUser teacherUser = getTableView().getItems().get(getIndex());
                    AppUtils.showYesNoOption(event, "Restore student " + teacherUser.getFull_name(), "Are you sure to restore this course?", new AlertActionInterface() {

                        @Override
                        public void action() throws IOException {
                            try {
                                teacherUserDAO.restore(teacherUser);
                                AppUtils.showInfo(event, "Restore student", "Restore student " + teacherUser.getFull_name() + " successfull", new AlertActionInterface() {
                                    @Override
                                    public void action() {
                                        reloadcallback.call(true);
                                    }
                                });
                            } catch (Exception e) {
                                AppUtils.showAlert(event, "Restore student", "Restore student " + teacherUser.getFull_name() + " false");
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
                    TeacherUser teacherUser = getTableView().getItems().get(getIndex());
                    if (teacherUser.getDeleted_at() == null) {
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


        this.getColumns().addAll(teacherId, userId, fullName, mobile, email, title, address, last_login, created_at, updated_at, deleted_at, action);

    }

    private <T> TableColumn<TeacherUser, T> createColumn(String title, String property) {
        TableColumn<TeacherUser, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }
}
