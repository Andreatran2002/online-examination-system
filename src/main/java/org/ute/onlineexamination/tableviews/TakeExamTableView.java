package org.ute.onlineexamination.tableviews;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ute.onlineexamination.models.TakeExam;

public class TakeExamTableView  extends TableView<TakeExam> {
        public TakeExamTableView(ObservableList<TakeExam> takeExams) {
            super(takeExams);

//            // Create TableColumn instances for each attribute
//            TableColumn<TakeExam, Integer> idColumn = this.createColumn("ID", "id");
//            TableColumn<TakeExam, String> nameColumn = this.createColumn("Name", "name");
//            TableColumn<TakeExam, Integer> ageColumn = this.createColumn("Age", "age");
//            TableColumn<TakeExam, String> genderColumn = this.createColumn("Gender", "gender");
//            TableColumn<User, String> languageColumn = this.createColumn("Language", "language");
//            TableColumn<User, String> countryColumn = this.createColumn("Country", "country");

            // Add the TableColumn instances to the TableView
//            this.getColumns().addAll(idColumn, nameColumn, ageColumn, genderColumn, languageColumn, countryColumn);
        }

        private <T> TableColumn<TakeExam, T> createColumn(String title, String property) {
            TableColumn<TakeExam, T> column = new TableColumn<>(title);
            column.setCellValueFactory(new PropertyValueFactory<>(property));
            return column;
        }

}
