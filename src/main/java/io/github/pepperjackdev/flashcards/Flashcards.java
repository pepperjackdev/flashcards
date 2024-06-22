package io.github.pepperjackdev.flashcards;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;

import io.github.pepperjackdev.flashcards.database.Collection;
import io.github.pepperjackdev.flashcards.database.Flashcard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Flashcards
    implements Initializable {

    private Collection collection;

    @FXML Button addFlashcards;
    @FXML Button goToCollections;

    @FXML VBox flashcardsList;

    void initData(Collection collection) {
        this.collection = collection;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<Flashcard> flashcards = collection.getFlashcards();

        for (Flashcard flashcard: flashcards) {
            // getting the loader of a new collection frame
            FXMLLoader loader = App.getLoader("flashcard_frame.fxml");

            // creating and initializing its controller
            FlashcardFrame controller = new FlashcardFrame();
            controller.initData(flashcard);

            // adding the controller to the loader
            loader.setController(controller);

            // adding the collection frame to the collections list
            try {
                flashcardsList.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
