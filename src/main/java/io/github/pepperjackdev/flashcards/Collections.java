package io.github.pepperjackdev.flashcards;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Collections {
    @FXML Button addCollection;
    @FXML VBox collectionsList;

    @FXML void initialize() {
        addCollection.setOnAction(e -> {
            Parent newCollectionFrame = App.loadFXML("collections/collection_frame.fxml");
            collectionsList.getChildren().add(newCollectionFrame);
        });
    }
}
