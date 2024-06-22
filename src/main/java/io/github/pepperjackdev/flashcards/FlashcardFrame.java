package io.github.pepperjackdev.flashcards;

import io.github.pepperjackdev.flashcards.database.Flashcard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FlashcardFrame {
    
    private Flashcard flashcard;

    @FXML TextField question;
    @FXML TextField answer;

    @FXML Button edit;
    @FXML Button delete;

    void initData(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    @FXML void initialize() {
        question.setText(flashcard.getQuestion());
        answer.setText(flashcard.getAnswer());

        delete.setOnAction(e -> {
            flashcard.getParentCollection().deleteFlashcard(flashcard.getFlashcardId());
            App.setRoot("flashcards.fxml");
        });
    }
}
