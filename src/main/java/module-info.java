module io.github.pepperjackdev.flashcards {
    requires javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires java.sql.rowset;

    opens io.github.pepperjackdev.flashcards to javafx.fxml;
    exports io.github.pepperjackdev.flashcards;
    exports io.github.pepperjackdev.flashcards.database;
}
