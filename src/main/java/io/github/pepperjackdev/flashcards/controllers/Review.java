package io.github.pepperjackdev.flashcards.controllers;

import static io.github.pepperjackdev.flashcards.constants.Constants.COLLECTIONS_FXML;

import io.github.pepperjackdev.flashcards.App;
import io.github.pepperjackdev.flashcards.controllers.loadable.Loadable;
import io.github.pepperjackdev.flashcards.core.ReviewSession;
import io.github.pepperjackdev.flashcards.database.Collection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Review 
    implements Loadable<Collection> {

    private Collection collection;
    private ReviewSession review;

    @FXML Label text;

    @FXML Button previous;
    @FXML Button stop;
    @FXML Button flip;
    @FXML Button flipAll;
    @FXML Button next;

    @Override
    public void load(Collection collection) {
        this.collection = collection;
        this.review = new ReviewSession(collection);
    }

    @FXML
    void initialize() {

        // let's init the data
        update(review.current().getQuestion());

        next.setOnAction(e -> {
            // FIXME
        });

        previous.setOnAction(e -> {
            // FIXME
        });

        stop.setOnAction(e -> {
            // end our review session, go back to collections
            App.setRoot(COLLECTIONS_FXML);
        });

        flip.setOnAction(e -> {
            // FIXME
        });

        flipAll.setOnAction(e -> {
            // FIXME
        });
    }

    public void update(String textToShow) {
        text.setText(textToShow);
    }
}
