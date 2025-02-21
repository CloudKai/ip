package mochi.mochi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mochi.exception.MochiException;
import mochi.task.TaskList;


public class ParserTest {
    private Parser parser;
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        parser = new Parser();
        taskList = new TaskList();
    }

    @Test
    void isExitCommand_validCommand_returnsTrue() {
        Assertions.assertTrue(parser.isExitCommand("bye"));
    }

    @Test
    void isExitCommand_invalidCommand_returnsFalse() {
        Assertions.assertFalse(parser.isExitCommand("byee"));
    }

    @Test
    void handleCommand_addTodoTask_success() throws MochiException {
        String response = parser.handleCommand("todo Buy groceries", taskList);
        Assertions.assertTrue(response.contains("Oooo Task added"));
        Assertions.assertEquals(1, taskList.size());
    }

    @Test
    void handleCommand_emptyList_returnsMessage() throws MochiException {
        String response = parser.handleCommand("list", taskList);
        Assertions.assertEquals("Your task list is empty!", response);
    }

    @Test
    void handleCommand_addDeadlineTask_success() throws MochiException {
        String response = parser.handleCommand("deadline Submit report /by 12-02-2025 1800", taskList);
        Assertions.assertTrue(response.contains("Oooo Task added"));
        Assertions.assertEquals(1, taskList.size());
    }

    @Test
    void handleCommand_addEventTask_success() throws MochiException {
        String response = parser.handleCommand("event Meeting /from 12-02-2025 1000 /to 12-02-2025 1200", taskList);
        Assertions.assertTrue(response.contains("Oooo Task added"));
        Assertions.assertEquals(1, taskList.size());
    }

    @Test
    void handleCommand_markTaskAsDone_success() throws MochiException {
        parser.handleCommand("todo Read book", taskList);
        String response = parser.handleCommand("mark 1", taskList);
        Assertions.assertTrue(response.contains("marked this as done"));
    }

    @Test
    void handleCommand_deleteTask_success() throws MochiException {
        parser.handleCommand("todo Exercise", taskList);
        String response = parser.handleCommand("delete 1", taskList);
        Assertions.assertTrue(response.contains("Task removed"));
        Assertions.assertEquals(0, taskList.size());
    }

    @Test
    void handleCommand_unknownCommand_throwsException() {
        MochiException exception = Assertions.assertThrows(MochiException.class, () -> {
            parser.handleCommand("hahaha", taskList);
        });
        Assertions.assertEquals("Sorry I don't recognise this command.", exception.getMessage());
    }
}
