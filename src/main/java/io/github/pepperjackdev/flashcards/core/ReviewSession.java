package io.github.pepperjackdev.flashcards.core;

import java.util.List;

import io.github.pepperjackdev.flashcards.database.Collection;
import io.github.pepperjackdev.flashcards.database.Flashcard;

public class ReviewSession {
    private Collection collection;
    private List<Flashcard> flashcards;
    private int currentFlashcardIndex = 0;

    public ReviewSession(Collection collection) {
        this.collection = collection;
        this.flashcards = this.collection.getFlashcards();
    }

    public boolean hasNextFlashcard() {
        return currentFlashcardIndex < flashcards.size();
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
