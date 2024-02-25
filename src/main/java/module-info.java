module org.ute.onlineexamination {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.ute.onlineexamination to javafx.fxml;
    exports org.ute.onlineexamination;
    exports org.ute.onlineexamination.controllers to javafx.fxml;
    opens org.ute.onlineexamination.controllers;
}