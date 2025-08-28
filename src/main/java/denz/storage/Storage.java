package denz.storage;

import denz.model.Task;
import denz.model.TaskList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading from and writing tasks to a save file on disk.
 */
public class Storage {
    /** Path to the save file. */
    private final Path path;

    /**
     * Creates a new {@code Storage} object for a given file path.
     *
     * @param filePath the path to the save file
     */
    public Storage(String filePath) {
        this.path = Paths.get(filePath);
    }

    /**
     * Loads tasks from the save file into a {@link TaskList}.
     * <p>
     * If the save file does not exist, a new one is created and an empty {@code TaskList} is returned.
     * Malformed lines are skipped gracefully.
     *
     * @return a {@link TaskList} containing all successfully loaded tasks
     */
    public TaskList load() {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            // if parent directory not made yet, creates parent directory
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            // if filename doesn't exist, create file and return empty list
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
                    // converts line into a task and adds to list
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

    /**
     * Saves a {@link TaskList} into the save file.
     * <p>
     * Overwrites the file contents if the file already exists, otherwise creates a new file.
     *
     * @param tasks the {@code TaskList} to save
     */
    public void save(TaskList tasks) {
        List<String> lines = new ArrayList<>();
        for (Task t : tasks.getList()) {
            lines.add(TaskIO.toSaveLine(t));
        }
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            // If the file exists, truncate then write; else create and write.
            Files.write(path, lines,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error writing save file: " + e.getMessage());
        }
    }
}
