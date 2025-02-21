package mochi.task;

import java.util.ArrayList;
import java.util.stream.Collectors;

import mochi.exception.MochiException;

/**
 * Manages a list of tasks in the Mochi application.
 * Provides methods to add, remove, retrieve, and modify tasks.
 */
public class TaskList {

    private ArrayList<Task> tasks;

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a task list with an existing list of tasks.
     *
     * @param tasks The list of tasks to initialize with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the task list at the specified index.
     *
     * @param index The index of the task to remove.
     * @return The removed task.
     * @throws MochiException If the index is out of bounds.
     */
    public Task removeTask(int index) throws MochiException {
        if (index < 0 || index >= tasks.size()) {
            throw new MochiException("Huh I can't find this task number.");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws MochiException If the index is out of bounds.
     */
    public Task getTask(int index) throws MochiException {
        if (index < 0 || index >= tasks.size()) {
            throw new MochiException("Invalid task number.");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task as done or not done based on the specified status.
     *
     * @param index  The index of the task to mark.
     * @param isDone True to mark the task as done, false to mark it as not done.
     * @throws MochiException If the index is out of bounds.
     */
    public void markTask(int index, boolean isDone) throws MochiException {
        Task task = getTask(index);
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
    }

    /**
     * Returns all tasks in the task list.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Finds tasks that match the given keyword in the description.
     *
     * @param keyword The keyword to search for in the task descriptions.
     * @return A list of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        return tasks.stream()
                .filter(task -> task.getDescription()
                                    .toLowerCase()
                                    .contains(keyword.toLowerCase()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    /**
     * Finds tasks that match the given keyword in the description.
     *
     * @param index The keyword to search for in the task descriptions.
     * @param field The keyword to search for in the task descriptions.
     * @param newValue The keyword to search for in the task descriptions.
     */
    public void updateTask(int index, String field, String newValue) throws MochiException {
        if (index < 0 || index >= tasks.size()) {
            throw new MochiException("I do not recognise this task number.");
        }

        Task task = tasks.get(index);

        if (task instanceof Todo todo) {
            if (field.equals("task")) {
                todo.setDescription(newValue);
            } else {
                throw new MochiException("Invalid <field> for todo. Use 'task'");
            }
        } else if (task instanceof Deadline deadline) {
            switch (field) {
            case "task": deadline.setDescription(newValue);
                break;
            case "by": deadline.setDeadline(newValue);
                 break;
            default:
                throw new MochiException("Invalid <field> for deadline. Use 'task' or 'by'.");
            }
        } else if (task instanceof Event event) {
            switch (field) {
            case "task": event.setDescription(newValue);
                break;
            case "from": event.setStartTime(newValue);
                break;
            case "to": event.setEndTime(newValue);
                break;
            default:
                throw new MochiException("Invalid <field> for event. Use 'task', 'from, or 'to'.");
            }
        } else {
            throw new MochiException("Invalid <field> for this task type.");
        }
    }
}
