package denz.command;

import java.time.LocalDateTime;

import denz.exception.AddException;
import denz.model.Deadline;
import denz.model.Task;
import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command to add a {@link Deadline} task to the task list.
 * <p>
 * A deadline task consists of a description and a due date/time.
 */
public class AddDeadlineCommand extends Command {
    private final String description;
    private final LocalDateTime by;

    /**
     * Creates a new {@code AddDeadlineCommand}.
     *
     * @param description Description of the deadline task.
     * @param by          Due date and time of the task.
     * @throws AddException If the description is null or blank.
     */
    public AddDeadlineCommand(String description, LocalDateTime by) throws AddException {
        if (description == null || description.isBlank()) {
            throw new AddException("wthelly, you're trolling me right. You are missing the task description");
        }
        this.description = description.trim();
        this.by = by;
    }

    /**
     * Executes the command by creating a new {@link Deadline} and adding it to the task list.
     * The new task is also saved to persistent storage.
     *
     * @param tasks   Task list to add the new task to.
     * @param ui      User interface to display feedback.
     * @param storage Storage to save the updated task list.
     * @throws AddException If the description is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AddException {
        Task t = new Deadline(description, by);
        tasks.add(t);
        ui.showTaskAdded(t, tasks.size());
        storage.save(tasks);
    }
}
