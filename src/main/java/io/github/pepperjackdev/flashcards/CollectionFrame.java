package io.github.pepperjackdev.flashcards;

import java.net.URL;
import java.util.ResourceBundle;

import io.github.pepperjackdev.flashcards.database.Collection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CollectionFrame
    implements Initializable {

    Collection collection;

    @FXML Label title;
    @FXML Label description;
    @FXML Label lastModified;

    @FXML Button view;
    @FXML Button revise;
    @FXML Button delete;

    @FXML void initData(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(collection.getTitle());
        description.setText(collection.getDescription());
        lastModified.setText(collection.getDatetimeOfLastModification().toString());
    }
}
