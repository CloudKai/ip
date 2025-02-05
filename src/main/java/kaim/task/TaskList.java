package kaim.task;

import kaim.KaiMException;
import java.util.ArrayList;

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
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
