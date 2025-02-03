package kaim.task;

import java.time.LocalDateTime;
import kaim.command.DateTime;
 
public class Deadline extends Task {
 
    private LocalDateTime deadlineDateTime;
 
     public Deadline(String description, String by) {
        super(description);
        this.deadlineDateTime = DateTime.parseDateTime(by);
     }
 
     @Override
     public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTime.formatDateTime(deadlineDateTime) + ")";        
     }
 }
