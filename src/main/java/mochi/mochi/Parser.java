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

    public static final String TODO_ERROR_MESSAGE = "Huh I do not get this Todo format.\n"
            + "Usage: todo <task>";
    public static final String DEADLINE_ERROR_MESSAGE = "Huh I do not get this Deadline format.\n"
            + "Usage: deadline <task> /by <yyyy-mm-dd HHmm>";
    public static final String EVENT_ERROR_MESSAGE = "Huh I do not get this Event format.\n"
            + "Usage: event <task> /from <start> /to <end>.\n"
            + "\n"
            + "Ref: The <start> and <end> format is <yyyy-mm-dd HHmm>";
    public static final String UPDATE_ERROR_MESSAGE = "Huh I do not get this Update format.\n"
            + "Usage: update <index> <field> <new_value>\n"
            + "\n"
            + "Ref: <field> = task  by   from   to";
    public static final String TASK_NOT_NUMBER_ERROR = "Huh the task_index must be a number";

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

        return "Friend here is your task list:\n" + sb.toString();
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
            throw new MochiException(TODO_ERROR_MESSAGE);
        }

        Task task = new Todo(parts[1].trim());
        tasks.addTask(task);
        return "Oooo Task added:\n" + tasks.size() + ". " + task + "\nFriend you have "
                + tasks.size() + " tasks in the list.";
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
            throw new MochiException(DEADLINE_ERROR_MESSAGE);
        }

        String[] details = parts[1].split("\\s*/by\\s*");
        if (details.length < 2) {
            throw new MochiException(DEADLINE_ERROR_MESSAGE);
        }

        if (details[0].isEmpty()) {
            throw new MochiException("Friend your task cannot be empty");
        }

        Task task = new Deadline(details[0].trim(), details[1].trim());
        tasks.addTask(task);
        return "Oooo Task added:\n" + tasks.size() + ". " + task + "\nFriend you have "
                + tasks.size() + " tasks in the list.";
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
            throw new MochiException(EVENT_ERROR_MESSAGE);
        }

        String[] details = parts[1].split("\\s*/from\\s*|\\s*/to\\s*");
        if (details.length < 3) {
            throw new MochiException(EVENT_ERROR_MESSAGE);
        }

        if (details[0].isEmpty()) {
            throw new MochiException("Friend your task cannot be empty");
        }

        Task task = new Event(details[0].trim(), details[1].trim(), details[2].trim());
        tasks.addTask(task);
        return "Oooo Task added:\n" + tasks.size() + ". " + task + "\nFriend you have "
                + tasks.size() + " tasks in the list.";
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
        if (parts.length < 2) {
            throw new MochiException("Friend, you must provide a task number to mark/unmark.");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new MochiException("Huh I can't find this task number.");
            }

            tasks.markTask(index, mark);
            if (mark) {
                return "Friend! I have marked this as done:\n" + tasks.getTask(index);
            } else {
                return "Friend! I have unmarked this as not done:\n" + tasks.getTask(index);
            }
        } catch (NumberFormatException e) {
            throw new MochiException(TASK_NOT_NUMBER_ERROR);
        }
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
        if (parts.length < 2) {
            throw new MochiException("Friend, you must provide an index to delete.");
        }

        try {
            int index = Integer.parseInt(parts[1]) - 1;
            Task removed = tasks.removeTask(index);
            return "Task removed:\n" + (index + 1) + ". " + removed + "\nFriend you have " + tasks.size()
                    + " tasks in the list.";
        } catch (NumberFormatException e) {
            throw new MochiException(TASK_NOT_NUMBER_ERROR);
        }
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
            throw new MochiException("Friend, you must provide a keyword to search.");
        }

        String keyword = parts[1].trim();
        ArrayList<Task> matchingTasks = tasks.findTasks(keyword);
        if (matchingTasks.isEmpty()) {
            return "Friend no tasks found matching: " + keyword;
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
            throw new MochiException(UPDATE_ERROR_MESSAGE);
        }

        String[] updateParts = parts[1].split(" ", 3);
        if (updateParts.length < 3) {
            throw new MochiException(UPDATE_ERROR_MESSAGE);
        }

        try {
            int index = Integer.parseInt(updateParts[0]) - 1;
            String field = updateParts[1];
            String newValue = updateParts[2];

            tasks.updateTask(index, field, newValue);
            return "Helped you update task:\n" + (index + 1) + ". " + tasks.getTask(index);
        } catch (NumberFormatException e) {
            throw new MochiException(TASK_NOT_NUMBER_ERROR);
        }
    }
}
