package mochi.mochi;

import java.util.ArrayList;

import mochi.exception.MochiException;
import mochi.task.Deadline;
import mochi.task.Event;
import mochi.task.Task;
import mochi.task.TaskList;
import mochi.task.Todo;

/**
 * This class handles parsing user input and executing the corresponding commands.
 * It interacts with the task list and adds, removes, or updates tasks based on the user's commands.
 */
public class Parser {

    /**
     * Checks if the given command is an exit command ("bye").
     *
     * @param command The command entered by the user.
     * @return true if the command is "bye", false otherwise.
     */
    public boolean isExitCommand(String command) {
        return command.equalsIgnoreCase("bye");
    }

    /**
     * Handles the command entered by the user and select the appropriate method.
     *
     * @param command The full command entered by the user.
     * @param tasks   The current task list to modify.
     * @return The response message after handling the command.
     * @throws MochiException If an error occurs while processing the command.
     */
    public String handleCommand(String command, TaskList tasks) throws MochiException {
        String[] parts = command.split(" ", 2);
        String action = parts[0];

        return switch (action) {
        case "hi" -> "Helloo my name is Mochi";
        case "list" -> listTasks(tasks);
        case "todo" -> addTodo(parts, tasks);
        case "deadline" -> addDeadline(parts, tasks);
        case "event" -> addEvent(parts, tasks);
        case "mark", "unmark" -> toggleTaskStatus(parts, tasks, action.equals("mark"));
        case "delete" -> deleteTask(parts, tasks);
        case "find" -> findTask(parts, tasks);
        case "update" -> updateTask(parts, tasks);
        default -> throw new MochiException("Sorry I don't recognise this command.");
        };
    }

    /**
     * Lists all tasks in the task list.
     *
     * @param tasks The current task list to retrieve tasks from.
     * @return A formatted string containing all tasks in the list.
     */
    private String listTasks(TaskList tasks) {
        if (tasks.size() == 0) {
            return "Your task list is empty!";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.getAllTasks().get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Adds a new Todo task to the task list.
     *
     * @param parts The command parts (description) for the todo task.
     * @param tasks The current task list to add the task to.
     * @return A response message indicating that the task has been added.
     * @throws MochiException If the description of the todo is empty.
     */
    private String addTodo(String[] parts, TaskList tasks) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(parts[1].trim());
        tasks.addTask(task);
        return "Added: " + task;
    }

    /**
     * Adds a new Deadline task to the task list.
     *
     * @param parts The command parts (description and deadline) for the task.
     * @param tasks The current task list to add the task to.
     * @return A response message indicating that the task has been added.
     * @throws MochiException If the deadline format is incorrect.
     */
    private String addDeadline(String[] parts, TaskList tasks) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException("Deadline format: deadline <task> /by <dd/MM/yyyy HHmm>");
        }
        String[] details = parts[1].split("\\s*/by\\s*");
        if (details.length < 2) {
            throw new MochiException("Deadline format: deadline <task> /by <dd/MM/yyyy HHmm>");
        }
        Task task = new Deadline(details[0].trim(), details[1].trim());
        tasks.addTask(task);
        return "Added: " + task;
    }

    /**
     * Adds a new Event task to the task list.
     *
     * @param parts The command parts (description, from, and to times) for the event task.
     * @param tasks The current task list to add the task to.
     * @return A response message indicating that the task has been added.
     * @throws MochiException If the event format is incorrect.
     */
    private String addEvent(String[] parts, TaskList tasks) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException("Event format: event <task> /from <start> /to <end>");
        }
        String[] details = parts[1].split("\\s*/from\\s*|\\s*/to\\s*");
        if (details.length < 3) {
            throw new MochiException("Event format: event <task> /from <start> /to <end>\n"
                                + "Example: event clean /from Monday /to Friday");
        }
        Task task = new Event(details[0].trim(), details[1].trim(), details[2].trim());
        tasks.addTask(task);
        return "Added: " + task;
    }

    /**
     * Marks or unmarks a task as done or not done.
     *
     * @param parts The command parts (task number) to toggle the task status.
     * @param tasks The current task list to modify.
     * @param mark  True to mark the task as done, false to mark it as not done.
     * @return A response message indicating the task status has been toggled.
     * @throws MochiException If the task index is invalid.
     */
    private String toggleTaskStatus(String[] parts, TaskList tasks, boolean mark) throws MochiException {
        int index = Integer.parseInt(parts[1]) - 1;
        tasks.markTask(index, mark);
        return mark ? "Marked as done: " + tasks.getTask(index) : "Marked as not done: " + tasks.getTask(index);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param parts The command parts (task number) to delete the task.
     * @param tasks The current task list to remove the task from.
     * @return A response message indicating the task has been deleted.
     * @throws MochiException If the task index is invalid.
     */
    private String deleteTask(String[] parts, TaskList tasks) throws MochiException {
        int index = Integer.parseInt(parts[1]) - 1;
        Task removed = tasks.removeTask(index);
        return "Deleted: " + removed;
    }

    /**
     * Finds tasks based on a keyword.
     *
     * @param parts The command parts, where parts[1] is the keyword.
     * @param tasks The task list to search in.
     * @return A string listing the matching tasks.
     * @throws MochiException If the user doesn't provide a keyword.
     */
    private String findTask(String[] parts, TaskList tasks) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException("You must provide a keyword to search.");
        }
        String keyword = parts[1].trim();
        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);

        if (matchingTasks.isEmpty()) {
            return "No tasks found matching: " + keyword;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append(i + 1).append(". ").append(matchingTasks.get(i)).append("\n");
        }

        return sb.toString();
    }

    private String updateTask(String[] parts, TaskList tasks) throws MochiException {
        if (parts.length < 2) {
            throw new MochiException("Invalid update format. Usage: update <index> <field> <new_value>\n"
                    + "Example: update 1 name do assignment");
        }
        String[] updateParts = parts[1].split(" ", 3);
        if (updateParts.length < 3) {
            throw new MochiException("Invalid update format. Usage: update <index> <field> <new_value>\n"
                    + "Example: update 1 name do assignment");
        }

        int index = Integer.parseInt(updateParts[0]) - 1;
        String field = updateParts[1];
        String newValue = updateParts[2];

        tasks.updateTask(index, field, newValue);
        return "Updated task " + (index + 1) + " successfully.";
    }
}
