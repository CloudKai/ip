package mochi.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mochi.exception.MochiException;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(); // Reset task list before each test
        Task task = new Todo("Buy groceries");
        taskList.addTask(task);
    }

    @Test
    void addTask_validTask_success() {
        Task task = new Task("Throw trash");
        taskList.addTask(task);
        Assertions.assertEquals(2, taskList.size());
        Assertions.assertEquals(task, taskList.getAllTasks().get(1));
    }

    @Test
    void removeTask_validIndex_success() throws MochiException {
        Task task = new Task("Throw trash");
        taskList.addTask(task);
        Task removedTask = taskList.removeTask(1);
        Assertions.assertEquals(task, removedTask);
        Assertions.assertEquals(1, taskList.size());
    }

    @Test
    void getTask_validIndex() throws MochiException {
        Task task = taskList.getTask(0);
        Assertions.assertEquals("[T][ ] Buy groceries", task.toString());
    }

}
