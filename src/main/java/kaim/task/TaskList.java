package kaim.task;

import java.util.ArrayList;
import java.util.stream.Collectors;

import kaim.KaiMException;

/**
 * Manages a list of tasks in the KaiM application.
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
     * @throws KaiMException If the index is out of bounds.
     */
    public Task removeTask(int index) throws KaiMException {
        if (index < 0 || index >= tasks.size()) {
            throw new KaiMException("Invalid task number.");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws KaiMException If the index is out of bounds.
     */
    public Task getTask(int index) throws KaiMException {
        if (index < 0 || index >= tasks.size()) {
            throw new KaiMException("Invalid task number.");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task as done or not done based on the specified status.
     *
     * @param index  The index of the task to mark.
     * @param isDone True to mark the task as done, false to mark it as not done.
     * @throws KaiMException If the index is out of bounds.
     */
    public void markTask(int index, boolean isDone) throws KaiMException {
        Task task = getTask(index);
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
    }

    /**
     * Updates the name/description of a task at the specified index.
     *
     * @param index   The index of the task to update.
     * @param newName The new name for the task.
     * @throws KaiMException If the index is out of bounds.
     */
    public void updateTaskName(int index, String newName) throws KaiMException {
        if (index < 0 || index >= tasks.size()) {
            throw new KaiMException("Invalid task number.");
        }
        tasks.get(index).setDescription(newName);
    }

    /**
     * Updates the deadline of a Deadline task at the specified index.
     *
     * @param index       The index of the Deadline task to update.
     * @param newDeadline The new deadline for the task.
     * @throws KaiMException If the index is out of bounds or the task is not a Deadline.
     */
    public void updateDeadlineTime(int index, String newDeadline) throws KaiMException {
        if (index < 0 || index >= tasks.size() || !(tasks.get(index) instanceof Deadline)) {
            throw new KaiMException("Invalid deadline task number.");
        } (
            (Deadline) tasks.get(index)).setDeadline(newDeadline);
    }

    /**
     * Updates the start time of an Event task at the specified index.
     *
     * @param index        The index of the Event task to update.
     * @param newStartTime The new start time for the event.
     * @throws KaiMException If the index is out of bounds or the task is not an Event.
     */
    public void updateEventStartTime(int index, String newStartTime) throws KaiMException {
        if (index < 0 || index >= tasks.size() || !(tasks.get(index) instanceof Event)) {
            throw new KaiMException("Invalid event task number.");
        } (
            (Event) tasks.get(index)).setStartTime(newStartTime);
    }

    /**
     * Updates the end time of an Event task at the specified index.
     *
     * @param index      The index of the Event task to update.
     * @param newEndTime The new end time for the event.
     * @throws KaiMException If the index is out of bounds or the task is not an Event.
     */
    public void updateEventEndTime(int index, String newEndTime) throws KaiMException {
        if (index < 0 || index >= tasks.size() || !(tasks.get(index) instanceof Event)) {
            throw new KaiMException("Invalid event task number.");
        } (
            (Event) tasks.get(index)).setEndTime(newEndTime);
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
    public void updateTask(int index, String field, String newValue) throws KaiMException {
        if (index < 0 || index >= tasks.size()) {
            throw new KaiMException("Invalid task number.");
        }

        Task task = tasks.get(index);

        if (task instanceof Todo todo) {
            if (field.equals("name")) {
                todo.setDescription(newValue);
            }
        } else if (task instanceof Deadline deadline) {
            switch (field) {
            case "name": deadline.setDescription(newValue);
                break;
            case "by": deadline.setDeadline(newValue);
                 break;
            default:
                throw new KaiMException("Invalid field for deadline. Use 'name' or 'by'.");
            }
        } else if (task instanceof Event event) {
            switch (field) {
            case "name": event.setDescription(newValue);
                break;
            case "start": event.setStartTime(newValue);
                break;
            case "to": event.setEndTime(newValue);
                break;
            default:
                throw new KaiMException("Invalid field for event. Use 'name', 'start', or 'to'.");
            }
        } else {
            throw new KaiMException("Invalid field for this task type.");
        }
    }
}
