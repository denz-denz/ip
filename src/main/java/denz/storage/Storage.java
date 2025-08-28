package denz.storage;

import denz.model.Task;
import denz.model.TaskList;
import denz.util.DateTimeUtil;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
//For storing data into a File
public class Storage {
    private final Path path;
    public Storage(String filePath) {
        this.path = Paths.get(filePath);
    }
    public TaskList load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
                return new TaskList(tasks);
            }
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }
                try {
                    //converts line into a task and adds to list
                    tasks.add(TaskIO.fromSaveLine(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping bad line " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return new TaskList(tasks);
    }
    public void save(TaskList tasks) {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks.getList()) {
            lines.add(TaskIO.toSaveLine(t));
        }
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error writing save file: " + e.getMessage());
        }
    }
}
