package io.github.pepperjackdev.flashcards;

import java.util.List;

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

        List<Flashcard> flashcards = collection.getCollectionFlashcards();
        flashcards.forEach(f -> System.out.println(f.getQuestion() + " /// " + f.getAnswear()));

        collection.emptyCollection();
        db.deleteCollection(collection.getCollectionId());
    }
}
