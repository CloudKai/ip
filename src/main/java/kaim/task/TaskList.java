package kaim.task;

import kaim.KaiMException;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask(int index) throws KaiMException {
        if (index < 0 || index >= tasks.size()) {
            throw new KaiMException("Invalid task number.");
        }
        return tasks.remove(index);
    }

    public Task getTask(int index) throws KaiMException {
        if (index < 0 || index >= tasks.size()) {
            throw new KaiMException("Invalid task number.");
        }
        return tasks.get(index);
    }

    public void markTask(int index, boolean isDone) throws KaiMException {
        Task task = getTask(index);
        if (isDone) {
            task.markAsDone();
        } else {
            task.markAsNotDone();
        }
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

}
