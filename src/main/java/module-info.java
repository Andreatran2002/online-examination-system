module org.ute.onlineexamination {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;

    opens org.ute.onlineexamination to javafx.fxml;
    exports org.ute.onlineexamination;
    exports org.ute.onlineexamination.controllers to javafx.fxml;
    opens org.ute.onlineexamination.controllers;
    exports org.ute.onlineexamination.daos;
    opens org.ute.onlineexamination.daos to javafx.fxml;
    opens org.ute.onlineexamination.models to javafx.base;
}