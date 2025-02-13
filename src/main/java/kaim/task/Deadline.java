package kaim.task;

import java.time.LocalDateTime;

import kaim.command.DateTime;

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
   public Deadline(String description, String by) {
      super(description);
      this.deadlineDateTime = DateTime.parseDateTime(by);
   }

   @Override
   public String toString() {
      return "[D]" + super.toString() + " (by: " + DateTime.formatDateTime(deadlineDateTime) + ")";
   }
}
