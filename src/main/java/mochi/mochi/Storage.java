package mochi.mochi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import mochi.exception.MochiException;
import mochi.task.Task;
import mochi.task.TaskList;

/**
 * Handles loading and saving tasks to a file.
 * Reads stored tasks from a file,
 * and writing updated task lists back to the file.
 */
public class Storage {

    private String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath The path of the file where tasks will be stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * If the file does not exist, it creates a new file and returns an empty list.
     *
     * @return A list of tasks loaded from the file.
     * @throws MochiException If there is an error reading the file.
     */
    public ArrayList<Task> loadTasks() throws MochiException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(Paths.get(filePath))) {
                Files.createDirectories(Paths.get(filePath).getParent());
                Files.createFile(Paths.get(filePath));
            }

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(Task.fromString(line));
            }
            br.close();
        } catch (IOException e) {
            throw new MochiException("Error reading tasks from file.");
        }
        return tasks;
    }

    /**
     * Saves the current list of tasks to the storage file.
     *
     * @param tasks The TaskList containing tasks to be saved.
     * @throws MochiException If there is an error writing to the file.
     */
    public void saveTasks(TaskList tasks) throws MochiException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks.getAllTasks()) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new MochiException("Error saving tasks to file.");
        }
    }
}
