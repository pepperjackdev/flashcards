package io.github.pepperjackdev.flashcards.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

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
        int originalLength = collection.getFlashcards().size();

        for (Flashcard flashcard : collection.getFlashcards()) {
            if (flashcard.getDifficulty() != 0) {
                // let's add a number of duplicates equal to the difficulty
                // the number of duplicates should always be lower than the 1/8 of the total flashcards (from the original list)
                for (int i = 0; i < ((flashcard.getDifficulty() <= originalLength / 8) ? flashcard.getDifficulty() : originalLength / 4); i++) {
                    duplicates.add(flashcard);
                }
            }
        }

        // adding the duplicates to the original list
        flashcards.addAll(duplicates);
        flashcards = shuffle(flashcards);

    }

    public static List<Flashcard> shuffle(List<Flashcard> list) {
        // making sure that elents are shuffled without any equal elements next to each other
        
        if (list == null || list.size() <= 1) {
            return list;
        }

        Collections.shuffle(list);

        Map<Flashcard, Integer> frequencyMap = new HashMap<>();
        for (Flashcard element : list) {
            frequencyMap.put(element, frequencyMap.getOrDefault(element, 0) + 1);
        }

        PriorityQueue<Map.Entry<Flashcard, Integer>> maxHeap = new PriorityQueue<>(
            (a, b) -> b.getValue().compareTo(a.getValue())
        );

        maxHeap.addAll(frequencyMap.entrySet());

        List<Flashcard> result = new ArrayList<>(list.size());
        Queue<Map.Entry<Flashcard, Integer>> waitQueue = new LinkedList<>();

        while (!maxHeap.isEmpty()) {
            Map.Entry<Flashcard, Integer> current = maxHeap.poll();
            result.add(current.getKey());
            current.setValue(current.getValue() - 1);

            waitQueue.add(current);

            if (waitQueue.size() == 2) {
                Map.Entry<Flashcard, Integer> front = waitQueue.poll();
                if (front.getValue() > 0) {
                    maxHeap.add(front);
                }
            }
        }

        return result;
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
