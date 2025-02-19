package kaim.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kaim.KaiMException;

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
    void removeTask_validIndex_success() throws KaiMException {
        Task task = new Task("Throw trash");
        taskList.addTask(task);
        Task removedTask = taskList.removeTask(1);
        Assertions.assertEquals(task, removedTask);
        Assertions.assertEquals(1, taskList.size());
    }

    @Test
    void getTask_validIndex() throws KaiMException {
        Task task = taskList.getTask(0);
        Assertions.assertEquals("[T][ ] Buy groceries", task.toString());
    }

}
