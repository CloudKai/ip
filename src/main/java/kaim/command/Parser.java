package kaim.command;

import kaim.task.Deadline;
import kaim.task.Event;
import kaim.task.Task;
import kaim.task.TaskList;
import kaim.task.Todo;

import java.util.ArrayList;

import kaim.KaiMException;

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
     * @throws KaiMException If an error occurs while processing the command.
     */
    public String handleCommand(String command, TaskList tasks) throws KaiMException {
        String[] parts = command.split(" ", 2);
        String action = parts[0];

        switch (action) {
        case "list":
            return listTasks(tasks);
        case "todo":
            return addTodo(parts, tasks);
        case "deadline":
            return addDeadline(parts, tasks);
        case "event":
            return addEvent(parts, tasks);
        case "mark":
        case "unmark":
            return toggleTaskStatus(parts, tasks, action.equals("mark"));
        case "delete":
            return deleteTask(parts, tasks);
        case "find":
            return findTask(parts, tasks);
        default:
            throw new KaiMException("Unknown command.");
        }
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
     * @throws KaiMException If the description of the todo is empty.
     */
    private String addTodo(String[] parts, TaskList tasks) throws KaiMException {
        if (parts.length < 2) {
            throw new KaiMException("The description of a todo cannot be empty.");
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
     * @throws KaiMException If the deadline format is incorrect.
     */
    private String addDeadline(String[] parts, TaskList tasks) throws KaiMException {
        String[] details = parts[1].split("/by");
        if (details.length < 2) {
            throw new KaiMException("Deadline format: deadline task /by yyyy-MM-dd HHmm");
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
     * @throws KaiMException If the event format is incorrect.
     */
    private String addEvent(String[] parts, TaskList tasks) throws KaiMException {
        String[] details = parts[1].split("/from|/to");
        if (details.length < 3) {
            throw new KaiMException("Event format: event task /from start /to end");
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
     * @throws KaiMException If the task index is invalid.
     */
    private String toggleTaskStatus(String[] parts, TaskList tasks, boolean mark) throws KaiMException {
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
     * @throws KaiMException If the task index is invalid.
     */
    private String deleteTask(String[] parts, TaskList tasks) throws KaiMException {
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
     * @throws KaiMException If the user doesn't provide a keyword.
     */
    private String findTask(String[] parts, TaskList tasks) throws KaiMException {
        if (parts.length < 2) {
            throw new KaiMException("You must provide a keyword to search.");
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
}
