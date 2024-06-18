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
        Parent root = loadFXML("collections.fxml");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Flashcards");
        stage.show();
    }

    private static Parent loadFXML(String fxml) {
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
