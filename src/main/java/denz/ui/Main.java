package denz.ui;

import denz.app.Denz;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class represents the entry point to start the Denz chatbot GUI
 */

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/denz/ui/MainWindow.fxml"));
        AnchorPane root = loader.load();

        MainWindow controller = loader.getController();
        controller.setDenz(new Denz("data/denz.txt"));

        Scene scene = new Scene(root);
        stage.setTitle("Denz");
        stage.setMinWidth(400);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}

