package io.github.pepperjackdev.flashcards;

import java.io.IOException;

import io.github.pepperjackdev.flashcards.database.Flashcard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
            // reload the flashcards view
            
            // get the loader of the flashcards view
            FXMLLoader loader = App.getLoader("flashcards.fxml");
            Flashcards controller = new Flashcards();
            controller.initData(flashcard.getParentCollection());
            loader.setController(controller);
            
            flashcard.getParentCollection().deleteFlashcard(flashcard.getFlashcardId());
            
            try {
                App.setRoot(new Scene(loader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
        });

        edit.setOnAction(event -> {
            // if the text is edit, then we are editing
            if (edit.getText().equals("Edit")) {
                edit.setText("Save");
                question.setEditable(true);
                answer.setEditable(true);
                return;
            } else {
                edit.setText("Edit");
                flashcard.setQuestion(question.getText());
                flashcard.setAnswer(answer.getText());
                question.setEditable(false);
                answer.setEditable(false);
            }
        });
    }
}
