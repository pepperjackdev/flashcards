package io.github.pepperjackdev.flashcards.controllers;

import static io.github.pepperjackdev.flashcards.constants.Constants.COLLECTIONS_FXML;

import io.github.pepperjackdev.flashcards.App;
import io.github.pepperjackdev.flashcards.controllers.loadable.Loadable;
import io.github.pepperjackdev.flashcards.core.ReviewSession;
import io.github.pepperjackdev.flashcards.database.Collection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Review 
    implements Loadable<Collection> {

    @SuppressWarnings("unused")
    private Collection collection; // future implementation of other features
    private ReviewSession review;
    private boolean flipped = false;

    @FXML Label text;

    @FXML StackPane reviewFrame;
    @FXML Button previous;
    @FXML Button stop;
    @FXML Button flip;
    @FXML Button flipAll;
    @FXML Button easy;
    @FXML Button hard;

    @Override
    public void load(Collection collection) {
        this.collection = collection;
        this.review = new ReviewSession(collection);
    }

    @FXML
    void initialize() {

        // let's init the data
        update();

        easy.setOnAction(e -> {
            review.current().decreaseDifficulty();
            next();
        });

        hard.setOnAction(e -> {
            review.current().increaseDifficulty();
            next();
        });

        reviewFrame.setOnMouseClicked(e -> {
            // does the same as "flip"
            if (text.getText().equals(review.current().getQuestion())) {
                update(review.current().getAnswer());
            } else {
                update(review.current().getQuestion());
            }
        });

        previous.setOnAction(e -> {
            if (review.hasPreviousFlashcard()) {
                review.previous();
                update();
            }
        });

        stop.setOnAction(e -> {
            // end our review session, go back to collections
            App.setRoot(COLLECTIONS_FXML);
        });

        flip.setOnAction(e -> {
            if (text.getText().equals(review.current().getQuestion())) {
                update(review.current().getAnswer());
            } else {
                update(review.current().getQuestion());
            }
        });

        flipAll.setOnAction(e -> {
            this.flipped = !flipped;
            update();
        });
    }

    private void next() {
        if (review.hasNextFlashcard()) {
            review.next();
            update();
        } else {
            // end our review session, go back to collections
            App.setRoot(COLLECTIONS_FXML);
        }
    }

    public void update() {
        update(flipped ? review.current().getAnswer() : review.current().getQuestion());
    }

    public void update(String textToShow) {
        
        text.setText(textToShow);

        if (!review.hasPreviousFlashcard()) {
            previous.setDisable(true);
        } else {
            previous.setDisable(false);
        }

    }
}
