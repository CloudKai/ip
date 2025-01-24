
public class Event extends Task {

    protected String to;
    protected String start;

    public Event(String description, String start, String to) {
        super(description);
        this.start = start;
        this.to = to;        
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + to + ")";
    }
}