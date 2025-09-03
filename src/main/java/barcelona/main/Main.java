package barcelona.main;

import java.io.IOException;

import barcelona.Barcelona;
import barcelona.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main function for JavaFX Application
 */
public class Main extends Application {
    private Barcelona barcelona = new Barcelona("./data/duke.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setTitle("Barcelona - your personal Tasklist app");
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBarcelona(barcelona);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
