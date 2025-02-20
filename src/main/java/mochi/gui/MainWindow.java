package mochi.gui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import mochi.mochi.Mochi;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Mochi mochi;

    private Image bearImage = new Image(this.getClass().getResourceAsStream("/images/bear.jpg"));
    private Image mochiImage = new Image(this.getClass().getResourceAsStream("/images/mochi.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        showMessage("Hello! I'm Mochi\nWhat can I do for you?");
    }

    /** Injects the Mochi instance */
    public void setMochi(Mochi d) {
        mochi = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        String response = mochi.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, bearImage),
                DialogBox.getMochiDialog(response, mochiImage)
        );
        if (response.equals("Bye. Hope to see you again soon!")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
        userInput.clear();
    }

    /**
     * Displays a welcome message when the application starts.
     * This message introduces the application and asks the user for input.
     */
    public void showMessage(String message) {
        dialogContainer.getChildren().add(
                DialogBox.getMochiDialog(message, mochiImage)
        );
    }
}
