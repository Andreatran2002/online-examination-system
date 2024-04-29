package org.ute.onlineexamination.components;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.util.Builder;
import javafx.util.Callback;
import org.ute.onlineexamination.MainApplication;
import org.ute.onlineexamination.controllers.TestCardController;
import org.ute.onlineexamination.models.Examination;

import java.io.IOException;

public class TestCardBuilder  implements Builder<Pane> {

    Examination exam;
    Callback callback;

    public TestCardBuilder(Examination exam,Callback callback) {
        this.exam = exam;
        this.callback = callback;
    }


    @Override
    public Pane build() {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("TestCard.fxml"));
        TestCardController testCardController = new TestCardController(exam, new Callback() {
            @Override
            public Object call(Object param) {
                callback.call(param);
                return null;
            }
        });
        loader.setController(testCardController);
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
