package mochi.mochi;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mochi.gui.MainWindow;

/**
 * A GUI for Mochi using FXML.
 */
public class Main extends Application {

    private final Mochi mochi = new Mochi("data/Mochi.txt");

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("Mochi");
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMochi(mochi); // inject the Mochi instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
