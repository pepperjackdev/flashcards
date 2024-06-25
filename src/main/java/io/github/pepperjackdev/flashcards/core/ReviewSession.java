package io.github.pepperjackdev.flashcards.core;

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
