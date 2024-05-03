package io.github.pepperjackdev.flashcards.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    public String getId() {
        return collectionId;
    }

    public String getTitle() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getCollectionTitle = connection.prepareStatement("select title from collections where collectionId=?");
            getCollectionTitle.setString(1, collectionId);
            getCollectionTitle.execute();

            ResultSet rs = getCollectionTitle.getResultSet();
            return rs.getString("title");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDescription() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getCollectionDescription = connection.prepareStatement("select description from collections where collectionId=?");
            getCollectionDescription.setString(1, collectionId);
            getCollectionDescription.execute();

            ResultSet rs = getCollectionDescription.getResultSet();
            return rs.getString("description");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LocalDateTime getDatetimeOfCreation() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getCollectionDescription = connection.prepareStatement("select datetimeOfCreation from collections where collectionId=?");
            getCollectionDescription.setString(1, collectionId);
            getCollectionDescription.execute();

            ResultSet rs = getCollectionDescription.getResultSet();
            String timestamp = rs.getString("datetimeOfCreation");
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LocalDateTime getDatetimeOfLastModification() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getCollectionDescription = connection.prepareStatement("select datetimeOfLastModification from collections where collectionId=?");
            getCollectionDescription.setString(1, collectionId);
            getCollectionDescription.execute();

            ResultSet rs = getCollectionDescription.getResultSet();
            String timestamp = rs.getString("datetimeOfLastModification");
            return LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
}
