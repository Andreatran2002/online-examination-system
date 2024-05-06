package org.ute.onlineexamination.tableviews;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ute.onlineexamination.models.Examination;

import java.sql.Timestamp;

public class ExaminationTableView extends TableView<Examination> {
        public ExaminationTableView(ObservableList<Examination> examinations) {
            super(examinations);

            // Create TableColumn instances for each attribute
            TableColumn<Examination, String> nameColumn = this.createColumn("Name", "name");
            TableColumn<Examination, Timestamp> startColumn = this.createColumn("Start", "start");
            TableColumn<Examination, Timestamp> endColumn = this.createColumn("End", "end");
            TableColumn<Examination, Integer> scoring_typeColumn = this.createColumn("Scoring type", "scoring_type");

            this.getColumns().addAll(nameColumn, startColumn, endColumn,scoring_typeColumn);
        }

        private <T> TableColumn<Examination, T> createColumn(String title, String property) {
            TableColumn<Examination, T> column = new TableColumn<>(title);
            column.setCellValueFactory(new PropertyValueFactory<>(property));
            return column;
        }

}
