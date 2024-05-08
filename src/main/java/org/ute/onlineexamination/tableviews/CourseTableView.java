package org.ute.onlineexamination.tableviews;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.ute.onlineexamination.daos.CourseDAO;
import org.ute.onlineexamination.models.Course;
import org.ute.onlineexamination.models.StudentUser;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class CourseTableView extends TableView<Course> {
    Callback reloadcallback;
    public CourseTableView(ObservableList<Course> courses, Callback reloadcallback) {
        super(courses);
        this.reloadcallback = reloadcallback;

        TableColumn<Course, Integer> courseId = this.createColumn("Course Id", "id");
        courseId.setPrefWidth(40);
        TableColumn<Course, Integer> teacherId = this.createColumn("Teacher Id", "teacher_id");
        teacherId.setPrefWidth(40);
        TableColumn<Course, String> name = this.createColumn("Tên", "name");
        name.setPrefWidth(70);
        TableColumn<Course, String> description = this.createColumn("Mô tả", "description");
        description.setPrefWidth(70);
        TableColumn<Course, Timestamp> start = this.createColumn("Ngày bắt đầu", "start");
        start.setPrefWidth(126);
        TableColumn<Course, Timestamp> end = this.createColumn("Ngày kết thúc", "end");
        end.setPrefWidth(126);
        TableColumn<Course, String> category = this.createColumn("Phân loại", "category");
        category.setPrefWidth(100);
        TableColumn<Course, Timestamp> created_at = this.createColumn("Ngày tạo", "created_at");
        created_at.setPrefWidth(120);
        TableColumn<Course, Timestamp> updated_at = this.createColumn("Ngày cập nhật", "updated_at");
        updated_at.setPrefWidth(120);
        TableColumn<Course, Timestamp> deleted_at = this.createColumn("Ngày xoá", "deleted_at");
        deleted_at.setPrefWidth(120);
        TableColumn action = new TableColumn<>("Action");
        action.setPrefWidth(72);
        CourseDAO courseDAO = new CourseDAO();
        action.setCellFactory(param -> new TableCell<Course, Void>(){
            final Button btnDelete = new Button("Delete");
            final Button btnRetore = new Button("Restore");

            {
                btnDelete.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    AppUtils.showYesNoOption(event, "Delete course " + course.getName(), "Are you sure to delete this course?", new AlertActionInterface() {

                        @Override
                        public void action() throws IOException {
                            try {
                                courseDAO.delete(course);
                                AppUtils.showInfo(event, "Delete course", "Delete course " + course.getName() + " successfull", new AlertActionInterface() {
                                    @Override
                                    public void action() {
                                        reloadcallback.call(true);
                                    }
                                });
                            } catch (Exception e) {
                                AppUtils.showAlert(event, "Delete course", "Delete course " + course.getName() + " false");
                            }
                        }
                    });
                });
            }
            {
                btnRetore.setOnAction(event -> {
                    Course course = getTableView().getItems().get(getIndex());
                    AppUtils.showYesNoOption(event, "Restore course " + course.getName(), "Are you sure to restore this course?", new AlertActionInterface() {

                        @Override
                        public void action() throws IOException {
                            try {
                                courseDAO.restore(course);
                                AppUtils.showInfo(event, "Restore course", "Restore course " + course.getName() + " successfull", new AlertActionInterface() {
                                    @Override
                                    public void action() {
                                        reloadcallback.call(true);
                                    }
                                });
                            } catch (Exception e) {
                                AppUtils.showAlert(event, "Restore course", "Restore course " + course.getName() + " false");
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
                    Course course = getTableView().getItems().get(getIndex());
                    if (course.getDeleted_at() == null) {
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
        this.getColumns().addAll(courseId, teacherId, name, description ,start, end, category, created_at, deleted_at, updated_at,  action);

    }
    private <T> TableColumn<Course, T> createColumn(String title, String property) {
        TableColumn<Course, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }
}
