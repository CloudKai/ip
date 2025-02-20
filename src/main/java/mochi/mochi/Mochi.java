package mochi.mochi;

import mochi.exception.MochiException;
import mochi.task.TaskList;

/**
 * Mochi is the main class for the application. It initializes
 * and runs the application, handling user input and managing tasks.
 * The class loads tasks from a saved file, executes user commands, and saves tasks back to the file.
 */
public class Mochi {
    private final Storage storage;
    private TaskList tasks;

    /**
     * Initializes the Mochi instance with a specified file path. It sets up the user interface,
     * storage, and attempts to load tasks from the specified file. If loading tasks fails,
     * a new task list is created.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Mochi(String filePath) {
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (MochiException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        Parser parser = new Parser();
        try {
            if (parser.isExitCommand(input)) {
                return "Bye. Hope to see you again soon!";
            }
            String response = parser.handleCommand(input, tasks);
            storage.saveTasks(tasks);
            return response;
        } catch (MochiException e) {
            return e.getMessage();
        }
    }

}

