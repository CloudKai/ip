public class Parser {
    public boolean isExitCommand(String command) {
        return command.equalsIgnoreCase("bye");
    }

    public String handleCommand(String command, TaskList tasks) throws KaiMException {
        String[] parts = command.split(" ", 2);
        String action = parts[0];

        switch (action) {
            case "list":
                return listTasks(tasks);
            case "todo":
                return addTodo(parts, tasks);
            case "deadline":
                return addDeadline(parts, tasks);
            case "event":
                return addEvent(parts, tasks);
            case "mark":
            case "unmark":
                return toggleTaskStatus(parts, tasks, action.equals("mark"));
            case "delete":
                return deleteTask(parts, tasks);
            default:
                throw new KaiMException("Unknown command.");
        }
    }

    private String listTasks(TaskList tasks) {
        if (tasks.size() == 0) {
            return "Your task list is empty!";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.getAllTasks().get(i)).append("\n");
        }
        return sb.toString();
    }

    private String addTodo(String[] parts, TaskList tasks) throws KaiMException {
        if (parts.length < 2) {
            throw new KaiMException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(parts[1].trim());
        tasks.addTask(task);
        return "Added: " + task;
    }

    private String addDeadline(String[] parts, TaskList tasks) throws KaiMException {
        String[] details = parts[1].split("/by");
        if (details.length < 2) {
            throw new KaiMException("Deadline format: deadline task /by yyyy-MM-dd HHmm");
        }
        Task task = new Deadline(details[0].trim(), details[1].trim());
        tasks.addTask(task);
        return "Added: " + task;
    }

    private String addEvent(String[] parts, TaskList tasks) throws KaiMException {
        String[] details = parts[1].split("/from|/to");
        if (details.length < 3) {
            throw new KaiMException("Event format: event task /from start /to end");
        }
        Task task = new Event(details[0].trim(), details[1].trim(), details[2].trim());
        tasks.addTask(task);
        return "Added: " + task;
    }

    private String toggleTaskStatus(String[] parts, TaskList tasks, boolean mark) throws KaiMException {
        int index = Integer.parseInt(parts[1]) - 1;
        tasks.markTask(index, mark);
        return mark ? "Marked as done: " + tasks.getTask(index) : "Marked as not done: " + tasks.getTask(index);
    }

    private String deleteTask(String[] parts, TaskList tasks) throws KaiMException {
        int index = Integer.parseInt(parts[1]) - 1;
        Task removed = tasks.removeTask(index);
        return "Deleted: " + removed;
    }
}
