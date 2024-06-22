package io.github.pepperjackdev.flashcards;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import io.github.pepperjackdev.flashcards.database.Collection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CollectionFrame
    implements Initializable {

    private Collection collection;

    @FXML Label title;
    @FXML Label description;
    @FXML Label lastModified;

    @FXML Button view;
    @FXML Button revise;
    @FXML Button delete;

    void initData(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText(collection.getTitle());
        description.setText(collection.getDescription());
        lastModified.setText(collection.getDatetimeOfLastModification().toString());

        // initialize buttons

        delete.setOnAction(e -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete collection");
            alert.setHeaderText("Are you sure you want to delete this collection?");
            alert.showAndWait();

            if (alert.getResult().getText().equals("Cancel")) {
                return;
            }

            // delete collection
            App.database.deleteCollection(collection.getId());

            // let's refresh the collections view
            App.setRoot("collections.fxml");
        });

        view.setOnAction(e -> {
            FXMLLoader loader = App.getLoader("flashcards.fxml");
            
            Flashcards controller = new Flashcards();
            controller.initData(collection);

            loader.setController(controller);

            try {
                App.setRoot(new Scene(loader.load()));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });

        
    }
}
