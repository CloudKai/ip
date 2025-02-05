package kaim;

import kaim.task.TaskList;
import kaim.command.Parser;
import kaim.storage.Storage;
import kaim.ui.Ui;

/**
 * KaiM is the main class for the application. It initializes
 * and runs the application, handling user input and managing tasks.
 * The class loads tasks from a saved file, executes user commands, and saves tasks back to the file.
 */
public class KaiM {
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes the KaiM instance with a specified file path. It sets up the user interface,
     * storage, and attempts to load tasks from the specified file. If loading tasks fails, 
     * a new task list is created.
     * 
     * @param filePath The path to the file where tasks are stored.
     * @throws KaiMException If an error occurs while loading tasks from the file.
     */
    public KaiM(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (KaiMException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Starts the application by continuously accepting user input and executing commands until
     * the user enters the exit command. It interacts with the user interface and the storage
     * to manage tasks.
     * 
     * @throws KaiMException If an error occurs while handling a command or saving tasks.
     */
    public void run() {
        ui.showWelcomeMessage();
        Parser parser = new Parser();

        while (true) {
            String input = ui.readCommand();
            try {
                if (parser.isExitCommand(input)) {
                    ui.showExitMessage();
                    break;
                }
                String response = parser.handleCommand(input, tasks);
                ui.showMessage(response);
                storage.saveTasks(tasks);
            } catch (KaiMException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * The entry point of the application. It creates an instance of the KaiM class and runs the application.
     * 
     * @param args Command line arguments. Not used in this version of the application.
     */
    public static void main(String[] args) {
        new KaiM("data/KaiM.txt").run();
    }
}
