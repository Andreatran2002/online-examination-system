package org.ute.onlineexamination.tableviews;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.tablemodels.StudentExamScore;

import javax.security.auth.callback.Callback;
import java.sql.Timestamp;

public class StudentExamScoreTableView extends TableView<StudentExamScore> {
        public StudentExamScoreTableView(ObservableList<StudentExamScore> examinations) {
            super(examinations);

            // Create TableColumn instances for each attribute
            TableColumn<StudentExamScore, String> fullNameColumn = this.createColumn("Full Name", "fullName");
            TableColumn<StudentExamScore, String> emailColumn = this.createColumn("Email", "email");
            TableColumn<StudentExamScore, Double> scoreColumn = this.createColumn("Score", "score");
            TableColumn<StudentExamScore, Integer> timRetryColumn = this.createColumn("Time Retry", "timesRetry");

            this.getColumns().addAll(fullNameColumn, emailColumn, scoreColumn,timRetryColumn);
        }

        private <T> TableColumn<StudentExamScore, T> createColumn(String title, String property) {
            TableColumn<StudentExamScore, T> column = new TableColumn<>(title);
            column.setCellValueFactory(new PropertyValueFactory<>(property));
            return column;
        }

}
