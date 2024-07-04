package io.github.pepperjackdev.flashcards.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.pepperjackdev.flashcards.database.Collection;
import io.github.pepperjackdev.flashcards.database.Flashcard;

public class ReviewSession {
    @SuppressWarnings("unused")
    private Collection collection;
    
    private List<Flashcard> flashcards;
    private int currentFlashcardIndex = 0;

    public ReviewSession(Collection collection) {
        this.collection = collection;
        this.flashcards = collection.getFlashcards();

        // let's duplicate the hard flashcards to make them appear more often
        List<Flashcard> duplicates = new ArrayList<>();

        for (Flashcard flashcard : collection.getFlashcards()) {
            if (flashcard.getDifficulty() != 0) {
                // let's add a number of duplicates equal to the difficulty
                for (int i = 0; i < flashcard.getDifficulty(); i++) {
                    duplicates.add(flashcard);
                }
            }
        }

        flashcards.addAll(duplicates);
        Collections.shuffle(flashcards);
    }

    public boolean hasNextFlashcard() {
        return currentFlashcardIndex + 1 < flashcards.size();
    }

    public boolean hasPreviousFlashcard() {
        return currentFlashcardIndex > 0;
    }

    public Flashcard previous() {
        return flashcards.get(--currentFlashcardIndex);
    }

    public Flashcard current() {
        return flashcards.get(currentFlashcardIndex);
    }

    public Flashcard next() {
        return flashcards.get(++currentFlashcardIndex);
    }
}
