package org.ute.onlineexamination.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Builder;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.controllers.TestCardController;
import org.ute.onlineexamination.models.Examination;

import java.io.IOException;

public class TestCardBuilder  implements Builder<Pane> {

    Examination exam;

    public TestCardBuilder(Examination exam) {
        this.exam = exam;
    }

    @Override
    public Pane build() {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("TestCard.fxml"));
        TestCardController testCardController = new TestCardController(exam);
        loader.setController(testCardController);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
