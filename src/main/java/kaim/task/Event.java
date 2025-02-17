package kaim.task;

/**
 * Represents an event task with a start and end date/time.
 * An Event is a type of Task that includes a specified time frame.
 */
public class Event extends Task {

    private String startDateTime;
    private String endDateTime;

    /**
     * Constructs an Event task with a description, start time, and end time.
     *
     * @param description The description of the event.
     * @param start The start date of the event.
     * @param to The end date of the event.
     */
    public Event(String description, String start, String to) {
        super(description);
        this.startDateTime = start;
        this.endDateTime = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDateTime
            + " to: " + endDateTime + ")";
    }
}
