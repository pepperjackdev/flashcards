package io.github.pepperjackdev.flashcards.controllers;

import static io.github.pepperjackdev.flashcards.constants.Constants.COLLECTIONS_FXML;

import io.github.pepperjackdev.flashcards.App;
import io.github.pepperjackdev.flashcards.database.Collection;
import io.github.pepperjackdev.flashcards.database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CollectionCreator {
    
    @FXML TextField collectionTitle;
    @FXML TextField collectionDescription;
    @FXML Button addCollection;
    @FXML Button cancel;

    private Database db = new Database();

    @FXML void initialize() {

        collectionTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 32) {
                collectionTitle.setText(oldValue);
            }
        });

        collectionDescription.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 128) {
                collectionDescription.setText(oldValue);
            }
        });
        
        addCollection.setOnAction(e -> {
            String title = collectionTitle.getText();
            String description = collectionDescription.getText();

            Collection collection = db.createNewCollection(title, description);

            if (collection == null) { // the create function returns null if the title or description are invalid
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: invalid title or description");
                alert.setHeaderText("Title or description are invalid: please, use only letters, numbers, hyphens and colons. " +
                                    "Max length is 32 characters for title and 128 for description. Minimum length is 2 characters for both.");

                alert.showAndWait();
                return;
            }

            App.setRoot(COLLECTIONS_FXML);
        });

        cancel.setOnAction(e -> {
            App.setRoot(COLLECTIONS_FXML);
        });
    }
}
