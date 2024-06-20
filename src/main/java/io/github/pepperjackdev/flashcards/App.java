package io.github.pepperjackdev.flashcards;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App 
    extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(loadFXML("collections.fxml"));
        stage.setScene(scene);
        stage.setTitle("Flashcards");
        stage.sizeToScene();
        stage.show();

        // Set the minimum size of the window to the size of the scene
        stage.setMinHeight(scene.getHeight());
        stage.setMinWidth(scene.getWidth());
    }

    public static Parent loadFXML(String fxml) {
        // Load the FXML file and return the root node

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
