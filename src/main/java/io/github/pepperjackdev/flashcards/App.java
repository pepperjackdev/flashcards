package io.github.pepperjackdev.flashcards;

import io.github.pepperjackdev.flashcards.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App 
    extends Application{

    private static Stage stage;
    public static Database database = new Database();

    @Override
    public void start(Stage stage) throws Exception {
        App.stage = stage;
        stage.setTitle("Flashcards");

        setRoot("collections.fxml");
    }

    public static void setRoot(Scene scene) {
        // Set the root of the stage to the FXML file

        double width = stage.getScene().getWidth();

        stage.setScene(scene);
        stage.show();

        // Set the minimum size of the window to the size of the scene
        // stage.setMinHeight(scene.getHeight());
        // stage.setMinWidth(scene.getWidth());
    }

    public static void setRoot(String fxml) {
        setRoot(new Scene(loadFXML(fxml)));
    }

    public static Parent loadFXML(String fxml) {

        FXMLLoader loader = getLoader(fxml);

        try {
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FXMLLoader getLoader(String fxml) {

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
            return loader;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
