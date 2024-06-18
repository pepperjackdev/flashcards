package io.github.pepperjackdev.flashcards;

import io.github.pepperjackdev.flashcards.database.Collection;
import io.github.pepperjackdev.flashcards.database.Database;
import io.github.pepperjackdev.flashcards.database.Flashcard;

public class App {
    public static void main(String[] args) {
        Database db = new Database();
        Collection programmingMottos = db.createNewCollection("Programming Mottos", "A collection that shouldn't exist");
        Flashcard wrongFlashcard = programmingMottos.createNewFlashcard("Javian's motto", "Write Once, Debug Everywhere"); // isn't it Run Everywhere?
        wrongFlashcard.setAnswer("Write Once, Run Everywhere");

        // some info
        for (Collection c: db.getCollections()) {
            for (Flashcard f: c.getFlashcards()) {
                System.out.printf(">>> Q: %s -> A: %s\n", f.getQuestion(), f.getAnswer());
            }
        }
    }
}
