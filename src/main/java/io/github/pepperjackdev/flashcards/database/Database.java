package io.github.pepperjackdev.flashcards.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Database class allows to access and query the Applicantion's database
 * creating, updating, reading or deleting its records.
 */
public class Database {
    
    private final String connectionString;
    private final String path;

    public Database(String path) {
        this.path = path;
        this.connectionString = "jdbc:sqlite:" + this.path;

        if (!check()) {
            initialize();
        }
    }

    public Database() {
        this.path = "data.db";
        this.connectionString = "jdbc:sqlite:" + this.path;

        if (!check()) {
            initialize();
        }
    }

    public boolean check() {
        return Files.exists(Path.of(path));
    }

    public void initialize() {

        if (check()) {
            // if there's a database, it should be removed.
            try {
                Files.delete(Path.of(path));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try (Connection connection = DriverManager.getConnection(connectionString)) {

            // creating the flashcards table
            Statement initializeFlashcardsTable = connection.createStatement();
            initializeFlashcardsTable.execute(
                "create table flashcards (\r\n" + //
                    "\tflashcardId text,\r\n" + //
                    "\tquestion text,\r\n" + //
                    "\tanswer text,\r\n" + //
                    "\tdifficulty integer,\r\n" + //
                    "\tcollectionId text\r\n" + //
                ");"
            );

            // creating the flashcards collections table
            Statement initializeCollectionsTable = connection.createStatement();
            initializeCollectionsTable.execute(
                "create table collections (\r\n" + //
                    "\tcollectionId text,\r\n" + //
                    "\ttitle text,\r\n" + //
                    "\tdescription text,\r\n" + //
                    "\tdatetimeOfCreation text,\r\n" + //
                    "\tdatetimeOfLastModification text\r\n" + //
                ");"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Collection createNewCollection(String title, String description) {

        if (!title.matches(Collection.COLLECTION_TITLE_REGEX) || !description.matches(Collection.COLLECTION_DESCRIPTION_REGEX)) {
            return null;
        }

        String collectionId = UUID.randomUUID().toString();

        try (Connection connection = DriverManager.getConnection(connectionString)) {
            PreparedStatement createNewCollection = connection.prepareStatement(
                "insert into collections (collectionId, title, description, datetimeOfCreation, datetimeOfLastModification) values " +
                "(?, ?, ?, ?, ?)"
            );

            createNewCollection.setString(1, collectionId);
            createNewCollection.setString(2, title);
            createNewCollection.setString(3, description);
            createNewCollection.setString(4, LocalDateTime.now().toString());
            createNewCollection.setString(5, LocalDateTime.now().toString());

            createNewCollection.execute();

            return new Collection(connectionString, collectionId);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Collection> getCollections() {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            Statement getCollectionsIds = connection.createStatement();
            getCollectionsIds.execute("select collectionId from collections");

            ResultSet rs = getCollectionsIds.getResultSet();
            List<Collection> collections = new ArrayList<>();

            while (rs.next()) {
                String collectionId = rs.getString("collectionId");
                Collection toAdd = new Collection(connectionString, collectionId);
                collections.add(toAdd);
            }

            return collections;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteCollection(String collectionId) {
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            
            // deleting all collection's related flashcards
            Collection collection = new Collection(connectionString, collectionId);
            collection.emptyCollection();

            // deleting the collection's @collections record from db
            PreparedStatement deleteCollection = connection.prepareStatement(
                "delete from collections where collectionId=?"
            );

            deleteCollection.setString(1, collectionId);
            deleteCollection.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
