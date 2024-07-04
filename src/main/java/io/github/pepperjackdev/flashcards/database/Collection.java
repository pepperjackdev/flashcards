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
    public static final String COLLECTION_TITLE_REGEX = "[\\w\\-\\:\\ ]{2,32}";
    public static final String COLLECTION_DESCRIPTION_REGEX = "[\\w\\-\\:\\ ]{2,128}";

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

    public boolean setTitle(String title) {

        if (!title.matches(COLLECTION_TITLE_REGEX)) {
            return false;
        }

        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement updateCollectionTitle = connection.prepareStatement("update collections set title=? where collectionId=?");

            updateCollectionTitle.setString(1, title);
            updateCollectionTitle.setString(2, collectionId);

            updateCollectionTitle.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public boolean setDescription(String description) {

        if (!description.matches(COLLECTION_DESCRIPTION_REGEX)) {
            return false;
        }

        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement updateCollectionDescription = connection.prepareStatement("update collections set description=? where collectionId=?");

            updateCollectionDescription.setString(1, description);
            updateCollectionDescription.setString(2, collectionId);

            updateCollectionDescription.execute();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public void updateDatetimeOfLastModification() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement updateDatetimeOfLastModification = connection.prepareStatement("update collections set datetimeOfLastModification=? where collectionId=?");

            updateDatetimeOfLastModification.setString(1, LocalDateTime.now().toString());
            updateDatetimeOfLastModification.setString(2, collectionId);

            updateDatetimeOfLastModification.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Flashcard createNewFlashcard(String question, String answear) {
        String flashcardId = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(connectionString)) {
            
            PreparedStatement insertNewFlashcard = connection.prepareStatement(
                "insert into flashcards (flashcardId, question, answer, collectionId) values (?, ?, ?, ?)"
            );

            insertNewFlashcard.setString(1, flashcardId);
            insertNewFlashcard.setString(2, question);
            insertNewFlashcard.setString(3, answear);
            insertNewFlashcard.setString(4, collectionId);

            insertNewFlashcard.execute();

            updateDatetimeOfLastModification();
            return new Flashcard(flashcardId, connectionString);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Flashcard> getFlashcards() {
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

            updateDatetimeOfLastModification();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emptyCollection() {
        List<Flashcard> flashcards = getFlashcards();
        for (Flashcard flashcard: flashcards) {
            deleteFlashcard(flashcard.getFlashcardId());
        }
    }
}
