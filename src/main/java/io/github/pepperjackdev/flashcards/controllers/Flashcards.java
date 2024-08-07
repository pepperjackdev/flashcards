package io.github.pepperjackdev.flashcards.controllers;

import static io.github.pepperjackdev.flashcards.constants.Constants.COLLECTIONS_FXML;
import static io.github.pepperjackdev.flashcards.constants.Constants.FLASHCARD_FRAME_FXML;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import io.github.pepperjackdev.flashcards.App;
import io.github.pepperjackdev.flashcards.controllers.loadable.Loadable;
import io.github.pepperjackdev.flashcards.database.Collection;
import io.github.pepperjackdev.flashcards.database.Flashcard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Flashcards
    implements Initializable, Loadable<Collection> {

    private Collection collection;

    @FXML Button addFlashcard;
    @FXML Button goToCollections;

    @FXML TextField title;
    @FXML TextField description;

    @FXML VBox flashcardsList;

    public void load(Collection collection) {
        this.collection = collection;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //
        // load the flashcards from the collection
        //

        title.setText(collection.getTitle());
        description.setText(collection.getDescription());

        List<Flashcard> flashcards = collection.getFlashcards();

        for (Flashcard flashcard: flashcards) {
            FlashcardFrame controller = new FlashcardFrame();
            controller.load(flashcard);
            
            Parent scene = App.loadFXML(FLASHCARD_FRAME_FXML, controller);
            flashcardsList.getChildren().add(scene);
        }

        //
        // init the buttons behaviors
        //

        title.setOnKeyTyped(event -> {
            boolean result = collection.setTitle(title.getText());
            if (!result) {
                int caret = title.getCaretPosition();
                title.setText(collection.getTitle());
                title.positionCaret(caret);
            }
        });

        description.setOnKeyTyped(event -> {
            boolean result = collection.setDescription(description.getText());
            if (!result) {
                int caret = description.getCaretPosition();
                description.setText(collection.getDescription());
                description.positionCaret(caret);
            }
        });

        goToCollections.setOnAction(event -> {
            App.setRoot(COLLECTIONS_FXML);
        });

        addFlashcard.setOnAction(event -> {
            Flashcard flashcard = collection.createNewFlashcard("Question", "Answer");
            FlashcardFrame controller = new FlashcardFrame();
            controller.load(flashcard);            
            flashcardsList.getChildren().add(
                App.loadFXML(FLASHCARD_FRAME_FXML, controller)
            );
        });
    }
}
