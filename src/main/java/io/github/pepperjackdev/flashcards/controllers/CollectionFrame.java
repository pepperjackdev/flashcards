package io.github.pepperjackdev.flashcards.controllers;

import static io.github.pepperjackdev.flashcards.constants.Constants.COLLECTIONS_FXML;
import static io.github.pepperjackdev.flashcards.constants.Constants.FLASHCARDS_FXML;
import static io.github.pepperjackdev.flashcards.constants.Constants.REVIEW_COLLECTION_FXML;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import io.github.pepperjackdev.flashcards.App;
import io.github.pepperjackdev.flashcards.controllers.loadable.Loadable;
import io.github.pepperjackdev.flashcards.database.Collection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class CollectionFrame
    implements Initializable, Loadable<Collection> {

    private Collection collection;

    @FXML Label title;
    @FXML Label description;
    @FXML Label lastModified;

    @FXML Button view;
    @FXML Button review;
    @FXML Button delete;

    public void load(Collection collection) {
        this.collection = collection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //
        // init the shown data
        //

        title.setText(collection.getTitle());
        description.setText(collection.getDescription());
        lastModified.setText("Last modified " + collection.getDatetimeOfLastModification().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm").localizedBy(Locale.ENGLISH)));

        //
        // initialize the buttons behaviors
        //

        delete.setOnAction(e -> {

            // ask for confirmation
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
            App.setRoot(COLLECTIONS_FXML);
        });

        // view the collection
        view.setOnAction(event -> {
            Flashcards controller = new Flashcards();
            controller.load(collection);;
            App.setRoot(App.loadFXML(FLASHCARDS_FXML, controller));
        });

        review.setOnAction(event -> {

            if (collection.getFlashcards().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR, "This collection is empty... add at least one flashcard to start a review session.", ButtonType.OK);
                alert.setTitle("Empty Collection");
                alert.show();
                return;
            }

            Review controller = new Review();
            controller.load(collection);
            
            App.setRoot(
                App.loadFXML(REVIEW_COLLECTION_FXML, controller)
            );
        });
    }
}
