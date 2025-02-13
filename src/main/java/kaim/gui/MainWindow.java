package kaim.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import kaim.KaiM;

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

    private KaiM kaiM;

    private Image dogImage = new Image(this.getClass().getResourceAsStream("/images/Dog.jpg"));
    private Image catImage = new Image(this.getClass().getResourceAsStream("/images/Cat.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the KaiM instance */
    public void setKaim(KaiM d) {
        kaiM = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kaiM.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, catImage),
                DialogBox.getDukeDialog(response, dogImage)
        );
        userInput.clear();
    }
}
