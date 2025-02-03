import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Storage {

    private ArrayList<Task> tasks = new ArrayList<>();
    private String FILE_PATH;
    
    public Storage(String location) {
        this.FILE_PATH = location;
    }
    
    // Load tasks from the file
    public void loadTasks() {
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
    public void saveTasks() {
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
