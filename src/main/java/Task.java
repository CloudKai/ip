
public class Task {

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public static Task fromString(String taskString) {
        boolean isDone = taskString.startsWith("[X]");
        String description = "";
        System.out.println(description);

        if (taskString.startsWith("[T]")) {
            description =  taskString.substring(6).trim();
            System.out.println(description);
            Todo todo = new Todo(description);
            if (isDone) todo.markAsDone();
            return todo;

        } else if (taskString.startsWith("[D]")) {
            description =  taskString.substring(6).trim();
            String[] parts = description.split("/by");
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            if (isDone) deadline.markAsDone();
            return deadline;

        } else if (taskString.startsWith("[E]")) {
            description =  taskString.substring(6).trim();
            String[] parts = description.split("/from");
            String[] times = parts[1].split("/to");
            Event event = new Event(parts[0].trim(), times[0].trim(), times[1].trim());
            if (isDone) event.markAsDone();
            return event;

        } else {
            description = taskString.substring(4).trim();
            Task task = new Task(description);
            if (isDone) task.markAsDone();
            return task;
        }
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}