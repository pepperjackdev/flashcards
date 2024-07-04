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

    public void setQuestion(String question) {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement setQuestion = connection.prepareStatement("update flashcards set question=? where flashcardId=?");
            setQuestion.setString(1, question);
            setQuestion.setString(2, flashcardId);

            setQuestion.execute();

            getParentCollection().updateDatetimeOfLastModification();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAnswer() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getAnswear = connection.prepareStatement("select answer from flashcards where flashcardId=?");
            getAnswear.setString(1, this.flashcardId);

            getAnswear.execute();

            ResultSet answer = getAnswear.getResultSet();
            return answer.getString("answer");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setAnswer(String answer) {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement setAnswer = connection.prepareStatement("update flashcards set answer=? where flashcardId=?");
            setAnswer.setString(1, answer);
            setAnswer.setString(2, flashcardId);

            setAnswer.execute();

            getParentCollection().updateDatetimeOfLastModification();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getDifficulty() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement getDifficulty = connection.prepareStatement("select difficulty from flashcards where flashcardId=?");
            getDifficulty.setString(1, this.flashcardId);

            getDifficulty.execute();

            ResultSet difficulty = getDifficulty.getResultSet();
            return difficulty.getInt("difficulty");

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void setDifficulty(int difficulty) {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement setDifficulty = connection.prepareStatement("update flashcards set difficulty=? where flashcardId=?");
            setDifficulty.setInt(1, difficulty);
            setDifficulty.setString(2, flashcardId);

            setDifficulty.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void increaseDifficulty() {
        setDifficulty(getDifficulty() + 1);
    }

    public void decreaseDifficulty() {

        if (getDifficulty() == 0) {
            // nothing to do
            return;
        }

        setDifficulty(getDifficulty() - 1);
    }

    public Collection getParentCollection() {
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
