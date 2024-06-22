package io.github.pepperjackdev.flashcards;

import io.github.pepperjackdev.flashcards.database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static io.github.pepperjackdev.flashcards.constants.Constants.*;

import java.io.IOException;

public class App
    extends Application{

    private static Stage stage;
    public static Database database = new Database();

    @Override
    public void start(Stage stage) throws Exception {
        App.stage = stage;

        stage.setTitle("Flashcards");
        setRoot(COLLECTIONS_FXML);
        stage.show();
    }

    public static void setRoot(Scene scene) {
        stage.setScene(scene);

        // Set the minimum size of the window to the size of the scene
        // stage.setMinHeight(scene.getHeight());
        // stage.setMinWidth(scene.getWidth());
    }

    public static void setRoot(String fxml) {
        setRoot(new Scene(loadFXML(fxml)));
    }

    public static <T> Parent loadFXML(String fxml, T controller) {
        FXMLLoader loader = getLoader(fxml);
        loader.setController(controller);
        
        try {
            return loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static Parent loadFXML(String fxml) {
        FXMLLoader loader = getLoader(fxml);

        try {
            return loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static FXMLLoader getLoader(String fxml) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
        return loader;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
