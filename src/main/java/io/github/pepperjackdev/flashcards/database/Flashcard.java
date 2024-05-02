package io.github.pepperjackdev.flashcards.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Flashcard {
    private final String connectionString;
    private final String flashcardId;

    protected Flashcard(String flashcardId, String connectionString) {
        this.connectionString = connectionString;
        this.flashcardId = flashcardId;
    }

    public String getFlashcardId() {
        return flashcardId;
    }

    public String getQuestion() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getQuestion = connection.prepareStatement("select question from flashcards where flashcardId=?");
            getQuestion.setString(1, this.flashcardId);

            getQuestion.execute();

            ResultSet question = getQuestion.getResultSet();
            return question.getString("question");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAnswear() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getAnswear = connection.prepareStatement("select answear from flashcards where flashcardId=?");
            getAnswear.setString(1, this.flashcardId);

            getAnswear.execute();

            ResultSet answear = getAnswear.getResultSet();
            return answear.getString("answear");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Collection getCollection() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getCollectionId = connection.prepareStatement("select collectionId from flashcards where flashcardId=?");
            getCollectionId.setString(1, flashcardId);

            getCollectionId.execute();

            ResultSet rs = getCollectionId.getResultSet();
            String collectionId = rs.getString("collectionId");

            return new Collection(connectionString, collectionId);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
