import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


class KaiM {

    private static final String FILE_PATH = "data/KaiM.txt"; 
    private static final ArrayList<Task> tasks = new ArrayList<>();

    // Load tasks from the file
    private static void loadTasks() {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                tasks.add(Task.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }

    // Save tasks to the file
    private static void saveTasks() {
        try {
            Files.createDirectories(Paths.get("data"));

            try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Task task : tasks) {
                    fileWriter.write(task.toString());
                    fileWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
    
    // Main
    public static void main(String[] args) throws Exception {
        loadTasks();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out, true);

        pw.println(" Hello! I'm KaiM");
        pw.println(" What can I do for you?");

        while(tasks.size() < 100) {
            String task = br.readLine();

            try {
                if (task.equals(null)){
                    continue;
                }

                if(task.equals("bye")) {
                    pw.println(" Bye. Hope to see you again soon!");
                    break;

                } else if (task.equals("list")) {
                    if (tasks.size() < 1) {
                        throw new KaiMException("Your list is currently empty");
                    }
                    for (int i = 0; i < tasks.size(); i++) {
                        pw.println(" " + (i + 1) + ". " + tasks.get(i));
                    }

                } else if (task.split(" ")[0].equals("mark")){
                    int taskNumber = Integer.parseInt(task.split(" ")[1]) - 1;
                    tasks.get(taskNumber).markAsDone();
                    pw.println(" Nice! I've marked this task as done:");
                    pw.println("   " + tasks.get(taskNumber));

                } else if (task.split(" ")[0].equals("unmark")){
                    int taskNumber = Integer.parseInt(task.split(" ")[1]) - 1;
                    tasks.get(taskNumber).markAsNotDone();
                    pw.println(" OK, I've marked this task as not done yet:");
                    pw.println("   " + tasks.get(taskNumber));

                } else if (task.split(" ")[0].equals("delete")) {
                    int taskNumber = Integer.parseInt(task.split(" ")[1]) - 1;
                    if (taskNumber > tasks.size() - 1) {
                        throw new KaiMException("Incorrect input cannot delete task");
                    }
                    Task removedTask = tasks.remove(taskNumber);
                    pw.println(" Noted. I've removed this task:");
                    pw.println("   " + removedTask);
                    pw.println(" Now you have " + tasks.size() + " tasks in the list.");
                
                } else if (task.split(" ")[0].equals("todo")) {
                    String[] taskDescription = task.split("todo");
                    if (taskDescription.length == 0) {
                        throw new KaiMException("The description of a todo cannot be empty.");
                    }
                    tasks.add(new Todo(taskDescription[1].trim()));
                    pw.println(" Got it. I've added this task:");
                    pw.println("   " + tasks.get(tasks.size() - 1));
                    pw.println(" Now you have " + tasks.size() + " tasks in the list.");
                    
                } else if (task.split(" ")[0].equals("deadline")) {
                    String[] parts = task.split("/by");
                    String[] taskDescription = parts[0].split("deadline");
                    tasks.add(new Deadline(taskDescription[1].trim(), parts[1].trim()));
                    pw.println(" Got it. I've added this task:");
                    pw.println("   " + tasks.get(tasks.size() - 1));
                    pw.println(" Now you have " + tasks.size() + " tasks in the list.");

                } else if (task.split(" ")[0].equals("event")) {
                    String[] parts = task.split("/from");
                    String[] taskDescription = parts[0].split("event");
                    String[] taskTime = parts[1].split("/to");
                    tasks.add(new Event(taskDescription[1].trim(), taskTime[0].trim(), taskTime[1].trim()));
                    pw.println(" Got it. I've added this task:");
                    pw.println("   " + tasks.get(tasks.size() - 1));
                    pw.println(" Now you have " + tasks.size() + " tasks in the list.");

                } else {
                    if (task.split(" ").length == 1) {
                        throw new KaiMException("I'm sorry, unknown input please try again");
                    }

                    tasks.add(new Task(task));
                    pw.println(" added: " + task);
                }
            } catch (KaiMException e) {
                pw.println(e.getMessage());
            }
            saveTasks();
        }
        pw.close();
    }
}