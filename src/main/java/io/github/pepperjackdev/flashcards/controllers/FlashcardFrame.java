package io.github.pepperjackdev.flashcards.controllers;

import static io.github.pepperjackdev.flashcards.constants.Constants.FLASHCARDS_FXML;

import io.github.pepperjackdev.flashcards.App;
import io.github.pepperjackdev.flashcards.controllers.loadable.Loadable;
import io.github.pepperjackdev.flashcards.database.Flashcard;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class FlashcardFrame
    implements Loadable<Flashcard> {
    
    private Flashcard flashcard;

    @FXML TextArea question;
    @FXML TextArea answer;

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

        // make the question and answer to fit the rows of the content
        question.setPrefRowCount(question.getText().split("\n").length);
        answer.setPrefRowCount(answer.getText().split("\n").length);

        //
        // init the buttons behaviors
        //

        question.setOnKeyTyped(e -> {
            flashcard.setQuestion(question.getText());
            question.setPrefRowCount(question.getText().split("\n").length);
        });

        answer.setOnKeyTyped(e -> {
            flashcard.setAnswer(answer.getText());
            answer.setPrefRowCount(answer.getText().split("\n").length);
        });

        delete.setOnAction(e -> {
            // reload the flashcards view

            Flashcards controller = new Flashcards();
            controller.load(flashcard.getParentCollection());
            flashcard.getParentCollection().deleteFlashcard(flashcard.getFlashcardId());
            App.setRoot(
                App.loadFXML(FLASHCARDS_FXML, controller)
            );
            
        });
    }
}
