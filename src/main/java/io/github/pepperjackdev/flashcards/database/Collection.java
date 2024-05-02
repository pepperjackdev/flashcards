package io.github.pepperjackdev.flashcards.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Collection {
    private final String connectionString;
    private final String collectionId;

    protected Collection(String connectionString, String collectionId) {
        this.connectionString = connectionString;
        this.collectionId = collectionId;
    }

    public Flashcard createNewFlashcard(String question, String answear) {
        String flashcardId = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(connectionString)) {
            
            PreparedStatement insertNewFlashcard = connection.prepareStatement(
                "insert into flashcards (flashcardId, question, answear, collectionId) values (?, ?, ?, ?)"
            );

            insertNewFlashcard.setString(1, flashcardId);
            insertNewFlashcard.setString(2, question);
            insertNewFlashcard.setString(3, answear);
            insertNewFlashcard.setString(4, collectionId);

            insertNewFlashcard.execute();

            return new Flashcard(flashcardId, connectionString);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Flashcard> getCollectionFlashcards() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getCollectionFlashcardsIds = connection.prepareStatement("select flashcardId from flashcards where collectionId=?");
            getCollectionFlashcardsIds.setString(1, collectionId);

            getCollectionFlashcardsIds.execute();

            ResultSet collectionFlashcardsIds = getCollectionFlashcardsIds.getResultSet();
            List<Flashcard> collectionFlashcards = new ArrayList<>();

            while (collectionFlashcardsIds.next()) {
                Flashcard toAdd = new Flashcard(
                    collectionFlashcardsIds.getString("flashcardId"),
                    connectionString
                );

                collectionFlashcards.add(toAdd);
            }

            return collectionFlashcards;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    public void deleteFlashcard(String flashcardId) {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            
            PreparedStatement deleteFlashcard = connection.prepareStatement(
                "delete from flashcards where flashcardId=?"
            );

            deleteFlashcard.setString(1, flashcardId);

            deleteFlashcard.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emptyCollection() {
        List<Flashcard> flashcards = getCollectionFlashcards();
        for (Flashcard flashcard: flashcards) {
            deleteFlashcard(flashcard.getFlashcardId());
        }
    }
    
    public String getCollectionId() {
        return collectionId;
    }
}
