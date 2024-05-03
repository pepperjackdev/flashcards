package io.github.pepperjackdev.flashcards;

import io.github.pepperjackdev.flashcards.database.Collection;
import io.github.pepperjackdev.flashcards.database.Database;
import io.github.pepperjackdev.flashcards.database.Flashcard;

public class App {
    public static void main(String[] args) {
        Database db = new Database();
        Collection collection = db.createNewCollection("Computer science", "Programming paradigms");
        collection.createNewFlashcard("Java", "Object Oriented");
        collection.createNewFlashcard("C#", "Object Oriented");
        collection.createNewFlashcard("C++ (or Cpp)", "Procedural and Object Oriented");
        collection.createNewFlashcard("Haskell", "Functional");

        // for each collection into the database
        for (Collection c: db.getDatabaseCollections()) {
            System.out.println(">> Collection -> %s:".formatted(c.getTitle()));
            System.out.println(">>              %s".formatted(c.getDescription()));
            // for each flashcards of the collection
            for (Flashcard f: c.getCollectionFlashcards()) {
                System.out.println("\t - FC: %s? - %s".formatted(f.getQuestion(), f.getAnswear()));
            }

            if (c.getCollectionFlashcards().isEmpty()) {
                System.out.println("\t <empty>");
            }
        }
    }
}
