package org.ute.onlineexamination.tableviews;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.ute.onlineexamination.models.TakeExam;

import java.sql.Timestamp;

public class StudentTakeExamTableView extends TableView<TakeExam> {
        public StudentTakeExamTableView(ObservableList<TakeExam> TakeExams) {
            super(TakeExams);

            // Create TableColumn instances for each attribute
            TableColumn<TakeExam, Timestamp> startColumn = this.createColumn("Start", "start");
            TableColumn<TakeExam, Timestamp> endColumn = this.createColumn("End", "end");
            TableColumn<TakeExam, Integer> scoringColumn = this.createColumn("Scoring", "scoring");
            Callback<TableColumn<TakeExam, String>, TableCell<TakeExam, String>> courseCell = //
                    new Callback<TableColumn<TakeExam, String>, TableCell<TakeExam, String>>() {
                        @Override
                        public TableCell call(final TableColumn<TakeExam, String> param) {
                            final TableCell<TakeExam, String> cell = new TableCell<TakeExam, String>() {
                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        TakeExam q = getTableView().getItems().get(getIndex());

                                        setText(q.examination.getName());
                                    }
                                }
                            };
                            return cell;
                        }
                    };

            TableColumn<TakeExam, String> nameColumn = new TableColumn<>("Examination name");
            nameColumn.setCellFactory(courseCell);

            this.getColumns().addAll(nameColumn,startColumn, endColumn,scoringColumn);
        }

        private <T> TableColumn<TakeExam, T> createColumn(String title, String property) {
            TableColumn<TakeExam, T> column = new TableColumn<>(title);
            column.setCellValueFactory(new PropertyValueFactory<>(property));
            return column;
        }

}
