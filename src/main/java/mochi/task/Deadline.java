package mochi.task;

import java.time.LocalDateTime;

import mochi.exception.MochiException;
import mochi.util.DateTime;

/**
 * Represents a task with a deadline.
 * A Deadline is a type of Task that must be completed by a specific date and time.
 */
public class Deadline extends Task {
    private LocalDateTime deadlineDateTime;

    /**
     * Constructs a Deadline task with a description and a due date/time.
     *
     * @param description The description of the deadline task.
     * @param by The due date/time in string format.
     */
    public Deadline(String description, String by) throws MochiException {
        super(description);
        assert description != null && !description.isEmpty() : "Description cannot be null or empty";
        this.deadlineDateTime = DateTime.parseDateTime(by);
    }

    public void setDeadline(String newDeadline) throws MochiException {
        this.deadlineDateTime = DateTime.parseDateTime(newDeadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTime.formatDateTime(deadlineDateTime) + ")";
    }
}
