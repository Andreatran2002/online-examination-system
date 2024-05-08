package org.ute.onlineexamination.tableviews;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.ute.onlineexamination.daos.ExamDAO;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.utils.AlertActionInterface;
import org.ute.onlineexamination.utils.AppUtils;

import java.io.IOException;
import java.sql.Timestamp;

public class ExamTableView extends TableView<Examination> {
    Callback reloadcallback;
    public ExamTableView(ObservableList<Examination> exams, Callback reloadcallback) {
        super(exams);
        this.reloadcallback = reloadcallback;
        TableColumn<Examination, Integer> id = this.createColumn("Exam Id", "id");
        id.setPrefWidth(50);
        TableColumn<Examination, Integer> course_id = this.createColumn("Course Id", "course_id");
        course_id.setPrefWidth(50);
        TableColumn<Examination, String> name = this.createColumn("Tên", "name");
        name.setPrefWidth(80);
        TableColumn<Examination, Timestamp> start = this.createColumn("Ngày bắt đầu", "start");
        start.setPrefWidth(104);
        TableColumn<Examination, Timestamp> end = this.createColumn("Ngày kết thúc", "end");
        end.setPrefWidth(104);
        TableColumn<Examination, Integer> times_retry = this.createColumn("Số lần thử lại", "times_retry");
        times_retry.setPrefWidth(50);
        TableColumn<Examination, Integer> scoring_type = this.createColumn("Điểm", "scoring_type");
        scoring_type.setPrefWidth(50);
        TableColumn<Examination, String> description = this.createColumn("Mô tả", "description");
        description.setPrefWidth(80);
        TableColumn<Examination, Integer> total_minutes = this.createColumn("Tổng số thời gian", "total_minutes");
        total_minutes.setPrefWidth(50);
        TableColumn<Examination, Timestamp> created_at = this.createColumn("Ngày tạo", "created_at");
        created_at.setPrefWidth(104);
        TableColumn<Examination, Timestamp> updated_at = this.createColumn("Ngày cập nhật", "updated_at");
        updated_at.setPrefWidth(104);
        TableColumn<Examination, Timestamp> deleted_at = this.createColumn("Ngày xoá", "deleted_at");
        deleted_at.setPrefWidth(104);
        TableColumn action = new TableColumn<>("Action");
        action.setPrefWidth(70);
        ExamDAO examDAO = new ExamDAO();
        action.setCellFactory(param -> new TableCell<Examination, Void>(){
            final Button btnDelete = new Button("Delete");
            final Button btnRetore = new Button("Restore");

            {
                btnDelete.setOnAction(event -> {
                    Examination exam = getTableView().getItems().get(getIndex());
                    AppUtils.showYesNoOption(event, "Delete exam " + exam.getName(), "Are you sure to delete this exam?", new AlertActionInterface() {

                        @Override
                        public void action() throws IOException {
                            try {
                                examDAO.delete(exam);
                                AppUtils.showInfo(event, "Delete exam", "Delete exam " + exam.getName() + " successfull", new AlertActionInterface() {
                                    @Override
                                    public void action() {
                                        reloadcallback.call(true);
                                    }
                                });
                            } catch (Exception e) {
                                AppUtils.showAlert(event, "Delete exam", "Delete exam " + exam.getName() + " false");
                            }
                        }
                    });
                });
            }
            {
                btnRetore.setOnAction(event -> {
                    Examination exam = getTableView().getItems().get(getIndex());
                    AppUtils.showYesNoOption(event, "Restore exam " + exam.getName(), "Are you sure to restore this exam?", new AlertActionInterface() {

                        @Override
                        public void action() throws IOException {
                            try {
                                examDAO.restore(exam);
                                AppUtils.showInfo(event, "Restore exam", "Restore exam " + exam.getName() + " successfull", new AlertActionInterface() {
                                    @Override
                                    public void action() {
                                        reloadcallback.call(true);
                                    }
                                });
                            } catch (Exception e) {
                                AppUtils.showAlert(event, "Restore exam", "Restore exam " + exam.getName() + " false");
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
                    Examination exam = getTableView().getItems().get(getIndex());
                    if (exam.getDeleted_at() == null) {
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
        this.getColumns().addAll(id, course_id, name, description ,start, end, times_retry, scoring_type, total_minutes, created_at, deleted_at, updated_at, action);


    }
    private <T> TableColumn<Examination, T> createColumn(String title, String property) {
        TableColumn<Examination, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        return column;
    }
}
