package mochi.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import mochi.exception.MochiException;
import java.util.ArrayList;

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
        Task task = new Todo("Throw trash");
        taskList.addTask(task);
        Assertions.assertEquals(2, taskList.size());
        Assertions.assertEquals(task, taskList.getAllTasks().get(1));
    }

    @Test
    void removeTask_validIndex_success() throws MochiException {
        Task task = new Todo("Throw trash");
        taskList.addTask(task);
        Task removedTask = taskList.removeTask(1);
        Assertions.assertEquals(task, removedTask);
        Assertions.assertEquals(1, taskList.size());
    }

    @Test
    void getTask_validIndex_success() throws MochiException {
        Task task = taskList.getTask(0);
        Assertions.assertEquals("[T][ ] Buy groceries", task.toString());
    }

    @Test
    void removeTask_invalidIndex_throwsException() {
        Assertions.assertThrows(MochiException.class, () -> taskList.removeTask(10));
    }


    @Test
    void getTask_invalidIndex_throwsException() {
        Assertions.assertThrows(MochiException.class, () -> taskList.getTask(10));
    }

    @Test
    void markTask_asDone_success() throws MochiException {
        taskList.markTask(0, true);
        Assertions.assertEquals("[T][X] Buy groceries", taskList.getTask(0).toString());
    }

    @Test
    void markTask_asNotDone_success() throws MochiException {
        taskList.markTask(0, true);
        taskList.markTask(0, false);
        Assertions.assertEquals("[T][ ] Buy groceries", taskList.getTask(0).toString());
    }

    @Test
    void findTask_byKeyword_success() {
        ArrayList<Task> foundTasks = taskList.findTasks("groceries");
        Assertions.assertEquals(1, foundTasks.size());
        Assertions.assertEquals("[T][ ] Buy groceries", foundTasks.get(0).toString());
    }

    @Test
    void updateTask_todo_success() throws MochiException {
        taskList.updateTask(0, "task", "Buy vegetables");
        Assertions.assertEquals("[T][ ] Buy vegetables", taskList.getTask(0).toString());
    }

    @Test
    void updateTask_invalidIndex_throwsException() {
        Assertions.assertThrows(MochiException.class, () -> taskList.updateTask(10, "task", "New task"));
    }

    @Test
    void updateTask_invalidField_throwsException() {
        Assertions.assertThrows(MochiException.class, () -> taskList.updateTask(0, "deadline", "Tomorrow"));
    }
}
