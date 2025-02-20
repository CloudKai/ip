package mochi.task;

/**
 * Represents a to-do task in the Mochi application.
 * A to-do task is a basic task that only has a description and no date/time.
 */
public class Todo extends Task {

    /**
     * Constructs a new to-do task with the specified description.
     *
     * @param description The description of the to-do task.
     */
    public Todo(String description) {
        super(description);
        assert description != null && !description.isEmpty() : "Description cannot be null or empty";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
