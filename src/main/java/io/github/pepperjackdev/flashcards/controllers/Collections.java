package io.github.pepperjackdev.flashcards.controllers;

import static io.github.pepperjackdev.flashcards.constants.Constants.COLLECTION_CREATOR_FXML;
import static io.github.pepperjackdev.flashcards.constants.Constants.COLLECTION_FRAME_FXML;

import java.io.IOException;
import java.util.List;

import io.github.pepperjackdev.flashcards.App;
import io.github.pepperjackdev.flashcards.database.Collection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Collections {

    @FXML Button addCollection;
    @FXML VBox collectionsList;

    @FXML void initialize() throws IOException {

        //
        // loading the collections from the database
        //

        List<Collection> collections = App.database.getCollections();

        for (Collection collection: collections) {
            CollectionFrame controller = new CollectionFrame();
            controller.load(collection);
            collectionsList.getChildren().add(
                App.loadFXML(COLLECTION_FRAME_FXML, controller)
            );
        }

        //
        // init of the buttons behaviors
        //

        addCollection.setOnAction(e -> {
            App.setRoot(COLLECTION_CREATOR_FXML);
        });

    }
}
