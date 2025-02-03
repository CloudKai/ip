import java.time.LocalDateTime;

public class Event extends Task {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Event(String description, String start, String to) {
        super(description);
        this.startDateTime = DateTime.parseDateTime(start);
        this.endDateTime = DateTime.parseDateTime(to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + DateTime.formatDateTime(startDateTime) +
               " to: " + DateTime.formatDateTime(endDateTime) + ")";
    }
}