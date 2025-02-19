package kaim.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Represents the user interface for the KaiM application.
 * This class handles the input and output operations between the application and the user.
 */
public class Ui {
    private final BufferedReader br;
    private final PrintWriter pw;

    /**
     * Initializes the user interface with necessary input and output streams.
     * Sets up the BufferedReader to read from the console and PrintWriter to output to the console.
     */
    public Ui() {
        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(System.out, true);
    }

    /**
     * Displays a welcome message when the application starts.
     * This message introduces the application and asks the user for input.
     */
    public void showWelcomeMessage() {
        pw.println(" Hello! I'm KaiM");
        pw.println(" What can I do for you?");
    }

    /**
     * Reads a line of input from the user.
     *
     * @return The input entered by the user.
     * @throws RuntimeException If an error occurs while reading input.
     */
    public String readCommand() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error reading input.");
        }
    }

    /**
     * Displays a message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        pw.println(message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void showError(String errorMessage) {
        pw.println("Error: " + errorMessage);
    }

    /**
     * Displays a loading error message to the user.
     * This is shown when tasks cannot be loaded from a file.
     */
    public void showLoadingError() {
        pw.println("Error loading tasks from file.");
    }

    /**
     * Displays an exit message when the application ends.
     * This message is shown when the user exits the application.
     */
    public void showExitMessage() {
        pw.println(" Bye. Hope to see you again soon!");
    }
}
