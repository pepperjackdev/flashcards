package io.github.pepperjackdev.flashcards;

import java.io.IOException;
import java.util.List;

import io.github.pepperjackdev.flashcards.database.Collection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Collections {

    @FXML Button addCollection;
    @FXML VBox collectionsList;

    @FXML void initialize() throws IOException {
        // initializing the collections list
        List<Collection> collections = App.database.getCollections();
        for (Collection collection: collections) {
            // getting the loader of a new collection frame
            FXMLLoader loader = App.getLoader("collection_frame.fxml");

            // creating and initializing its controller
            CollectionFrame controller = new CollectionFrame();
            controller.initData(collection);

            // adding the controller to the loader
            loader.setController(controller);

            // adding the collection frame to the collections list
            collectionsList.getChildren().add(loader.load());
        }

        // initializing the behavior of the add collection button
        addCollection.setOnAction(e -> {
            App.setRoot("add_collection.fxml");
        });

    }
}
