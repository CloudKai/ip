package mochi.task;

import java.time.format.DateTimeParseException;

import mochi.exception.MochiException;

/**
 * Represents a task in the Mochi application.
 * A task has a description and a status indicating whether it is completed.
 */
public class Task {

    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new Task with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null && !description.isEmpty() : "Description cannot be null or empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     * "X" represents a completed task, and " " represents an incomplete task.
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Loads the corresponding task string based on the string representation,
     * and creates an object for the task.
     * The task type can be a Todo, Deadline, or Event.
     *
     * @param taskString The string representation of the task.
     * @return The corresponding Task object.
     * @throws IllegalArgumentException If the task string format is invalid.
     */
    public static Task fromString(String taskString) throws MochiException {
        boolean isDone = taskString.contains("[X]");
        String description = "";

        try {
            if (taskString.startsWith("[T]")) {
                return createTodoTask(taskString, isDone);
            } else if (taskString.startsWith("[D]")) {
                return createDeadlineTask(taskString, isDone);
            } else if (taskString.startsWith("[E]")) {
                return createEventTask(taskString, isDone);
            } else {
                return createGenericTask(taskString, isDone);
            }
        } catch (DateTimeParseException e) {
            throw new MochiException("I have issue with parsing date: " + e.getMessage());
        } catch (Exception e) {
            throw new MochiException("I have issue with parsing task: " + taskString + "\n" + e.getMessage());
        }
    }

    private static Todo createTodoTask(String taskString, boolean isDone) {
        String description = taskString.substring(6).trim();
        Todo todo = new Todo(description);
        if (isDone) {
            todo.markAsDone();
        }
        return todo;
    }

    private static Deadline createDeadlineTask(String taskString, boolean isDone) throws MochiException {
        String description = taskString.substring(6).trim();

        int startIdx = description.indexOf("(by: ");
        if (startIdx == -1) {
            throw new IllegalArgumentException("Invalid deadline format: " + taskString);
        }
        String taskDesc = description.substring(0, startIdx).trim();
        String dateTimeStr = description.substring(startIdx + 5, description.length() - 1)
                .trim(); // Remove (by: and )

        Deadline deadline = new Deadline(taskDesc, dateTimeStr);
        if (isDone) {
            deadline.markAsDone();
        }
        return deadline;
    }

    private static Event createEventTask(String taskString, boolean isDone) throws MochiException {
        String description = taskString.substring(6).trim();

        // Extract (from: Dec 02 2019, 6:00pm to: Dec 03 2019, 8:00am)
        int startIdx = description.indexOf("(from: ");
        int toIdx = description.indexOf(" to: ");
        if (startIdx == -1 || toIdx == -1) {
            throw new IllegalArgumentException("Invalid event format: " + taskString);
        }

        String taskDesc = description.substring(0, startIdx).trim();
        String fromTimeStr = description.substring(startIdx + 7, toIdx).trim();
        String toTimeStr = description.substring(toIdx + 5, description.length() - 1).trim(); // Remove )

        Event event = new Event(taskDesc, fromTimeStr, toTimeStr);
        if (isDone) {
            event.markAsDone();
        }
        return event;
    }

    private static Task createGenericTask(String taskString, boolean isDone) {
        String description = taskString.substring(4).trim();
        Task task = new Task(description);
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}
