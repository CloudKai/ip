package kaim.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Ui {
    private BufferedReader br;
    private PrintWriter pw;

    public Ui() {
        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(System.out, true);
    }

    public void showWelcomeMessage() {
        pw.println(" Hello! I'm KaiM");
        pw.println(" What can I do for you?");
    }

    public String readCommand() {
        try {
            return br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error reading input.");
        }
    }

    public void showMessage(String message) {
        pw.println(message);
    }

    public void showError(String errorMessage) {
        pw.println("Error: " + errorMessage);
    }

    public void showLoadingError() {
        pw.println("Error loading tasks from file.");
    }

    public void showExitMessage() {
        pw.println(" Bye. Hope to see you again soon!");
    }
}
