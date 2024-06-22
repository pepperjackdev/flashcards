package io.github.pepperjackdev.flashcards.controllers;

import io.github.pepperjackdev.flashcards.App;
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
        
        addCollection.setOnAction(e -> {
            String title = collectionTitle.getText();
            String description = collectionDescription.getText();

            if (title.isEmpty() || description.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error: invalid title or description");
                alert.setHeaderText("Title or description is empty: please, fill in the required fields");
                alert.showAndWait();
            }

            db.createNewCollection(title, description);
            App.setRoot("collections.fxml");
        });

        cancel.setOnAction(e -> {
            App.setRoot("collections.fxml");
        });
    }
}
