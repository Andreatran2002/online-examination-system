package org.ute.onlineexamination.tableviews;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ute.onlineexamination.models.Examination;
import org.ute.onlineexamination.models.tablemodels.StudentCourseOverview;

import java.sql.Timestamp;

public class StudentCourseOverviewTableView  extends TableView<StudentCourseOverview> {
        public StudentCourseOverviewTableView(ObservableList<StudentCourseOverview> studentCourseOverviews) {
            super(studentCourseOverviews);

            // Create TableColumn instances for each attribute
            TableColumn<StudentCourseOverview, String> fullNameColumn = this.createColumn("Full name", "fullName");
            TableColumn<StudentCourseOverview, String> emailColumn = this.createColumn("Email", "email");
            TableColumn<StudentCourseOverview, String> mobileColumn = this.createColumn("Mobile", "mobile");
            TableColumn<StudentCourseOverview, Integer> totalTestDoneColumn = this.createColumn("Total finished test", "totalTestDone");
            TableColumn<StudentCourseOverview, Timestamp> registerAtColumn = this.createColumn("Register Date", "registerAt");

            this.getColumns().addAll(fullNameColumn, emailColumn, mobileColumn,totalTestDoneColumn,registerAtColumn);
        }

        private <T> TableColumn<StudentCourseOverview, T> createColumn(String title, String property) {
            TableColumn<StudentCourseOverview, T> column = new TableColumn<>(title);
            column.setCellValueFactory(new PropertyValueFactory<>(property));
            return column;
        }

}
