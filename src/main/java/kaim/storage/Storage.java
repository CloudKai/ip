package kaim.storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import kaim.task.TaskList;
import kaim.task.Task;

import kaim.KaiMException;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> loadTasks() throws KaiMException {
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
            throw new KaiMException("Error reading tasks from file.");
        }
        return tasks;
    }

    public void saveTasks(TaskList tasks) throws KaiMException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks.getAllTasks()) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new KaiMException("Error saving tasks to file.");
        }
    }
}
