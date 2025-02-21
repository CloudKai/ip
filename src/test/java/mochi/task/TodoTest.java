package mochi.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    void createTodo_correctFormat() {
        Todo todo = new Todo("Buy groceries");
        Assertions.assertEquals("[T][ ] Buy groceries", todo.toString());
    }

    @Test
    void markTodoAsDone_correctFormat() {
        Todo todo = new Todo("Go jogging");
        todo.markAsDone();
        Assertions.assertEquals("[T][X] Go jogging", todo.toString());
    }

    @Test
    void markTodoAsNotDone_correctFormat() {
        Todo todo = new Todo("Read a book");
        todo.markAsDone();
        todo.markAsNotDone();
        Assertions.assertEquals("[T][ ] Read a book", todo.toString());
    }

    @Test
    void createTodo_nullDescription_throwsAssertionError() {
        Assertions.assertThrows(AssertionError.class, () -> {
            new Todo(null);
        });
    }

    @Test
    void createTodo_emptyDescription_throwsAssertionError() {
        Assertions.assertThrows(AssertionError.class, () -> {
            new Todo("");
        });
    }

    @Test
    void getDescription_correctValue() {
        Todo todo = new Todo("Finish homework");
        Assertions.assertEquals("Finish homework", todo.getDescription());
    }

}
