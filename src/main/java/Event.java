public class Event extends Task {

    private String startDateTime;
    private String endDateTime;

    public Event(String description, String start, String to) {
        super(description);
        this.startDateTime = start;
        this.endDateTime = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDateTime +
               " to: " + endDateTime + ")";
    }
}