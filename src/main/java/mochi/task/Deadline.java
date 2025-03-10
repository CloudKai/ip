package mochi.task;

import java.time.LocalDateTime;

import mochi.exception.MochiException;
import mochi.util.DateTimeUtil;

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
        this.deadlineDateTime = DateTimeUtil.parseDateTime(by);
    }

    public void setDeadline(String newDeadline) throws MochiException {
        try {
            this.deadlineDateTime = DateTimeUtil.parseDateTime(newDeadline);
        } catch (MochiException e) {
            throw new MochiException("Huh please use this date-time format: yyyy-mm-dd HHmm");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.formatDateTime(deadlineDateTime) + ")";
    }
}
