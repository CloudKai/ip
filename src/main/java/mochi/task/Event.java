package mochi.task;

import java.time.LocalDateTime;

import mochi.exception.MochiException;
import mochi.util.DateTime;

/**
 * Represents an event task with a start and end date/time.
 * An Event is a type of Task that includes a specified time frame.
 */
public class Event extends Task {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param start The start date of the event.
     * @param to The end date of the event.
     */
    public Event(String description, String start, String to) throws MochiException {
        super(description);
        assert description != null && !description.isEmpty() : "Description cannot be null or empty";
        this.startDateTime = DateTime.parseDateTime(start);
        this.endDateTime = DateTime.parseDateTime(to);
    }

    public void setStartTime(String newStartTime) throws MochiException {
        try {
            this.startDateTime = DateTime.parseDateTime(newStartTime);
        } catch (MochiException e) {
            throw new MochiException("Huh please use this date-time format: yyyy-mm-dd HHmm");
        }
    }

    public void setEndTime(String newEndTime) throws MochiException {
        try {
            this.endDateTime = DateTime.parseDateTime(newEndTime);
        } catch (MochiException e) {
            throw new MochiException("Huh please use this date-time format: yyyy-mm-dd HHmm");
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTime.formatDateTime(startDateTime)
            + ", to: " + DateTime.formatDateTime(endDateTime) + ")";
    }
}
