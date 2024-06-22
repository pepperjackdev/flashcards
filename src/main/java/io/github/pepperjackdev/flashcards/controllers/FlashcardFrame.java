package io.github.pepperjackdev.flashcards.controllers;

import static io.github.pepperjackdev.flashcards.constants.Constants.FLASHCARDS_FXML;

import io.github.pepperjackdev.flashcards.App;
import io.github.pepperjackdev.flashcards.controllers.loadable.Loadable;
import io.github.pepperjackdev.flashcards.database.Flashcard;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FlashcardFrame
    implements Loadable<Flashcard> {
    
    private Flashcard flashcard;

    @FXML Label question;
    @FXML Label answer;

    @FXML Button edit;
    @FXML Button delete;

    public void load(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    @FXML void initialize() {

        //
        // load the flashcard data
        //

        question.setText(flashcard.getQuestion());
        answer.setText(flashcard.getAnswer());

        //
        // init the buttons behaviors
        //

        delete.setOnAction(e -> {
            // reload the flashcards view

            Flashcards controller = new Flashcards();
            controller.load(flashcard.getParentCollection());
            flashcard.getParentCollection().deleteFlashcard(flashcard.getFlashcardId());
            App.setRoot(
                new Scene(App.loadFXML(FLASHCARDS_FXML, controller))
            );
            
        });
    }
}
