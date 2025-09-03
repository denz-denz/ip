package denz.command;

import java.time.LocalDateTime;

import denz.exception.AddException;
import denz.model.Event;
import denz.model.Task;
import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command to add an {@link denz.model.Event} task to the task list.
 * <p>
 * An event task consists of a description, a start time, and an end time.
 */
public class AddEventCommand extends Command {
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates a new {@code AddEventCommand}.
     *
     * @param description Description of the event task.
     * @param start       Start date and time of the event.
     * @param end         End date and time of the event.
     * @throws AddException If the description is null/blank, or if {@code end} is not after {@code start}.
     */
    public AddEventCommand(String description, LocalDateTime start, LocalDateTime end) throws AddException {
        if (description == null || description.isBlank()) {
            throw new AddException("wthelly, you're trolling me right. You are missing the task description");
        }
        this.description = description.trim();
        this.start = start;
        this.end = end;
        if (!end.isAfter(start)) {
            throw new AddException("denz.model.Event end must be after start.");
        }
    }

    /**
     * Executes the command by creating a new {@link denz.model.Event} and adding it to the task list.
     * The new task is also saved to persistent storage.
     *
     * @param tasks   Task list to add the new event to.
     * @param ui      User interface to display feedback.
     * @param storage Storage to save the updated task list.
     * @throws AddException If the event description or times are invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AddException {
        Task t = new Event(description, start, end);
        tasks.add(t);
        ui.showTaskAdded(t, tasks.size());
        storage.save(tasks);
    }

    @Override
    public String executeGui(TaskList tasks, Ui ui, Storage storage) throws AddException {
        Task t = new Event(description, start, end);
        tasks.add(t);
        String reply = ui.showTaskAddedGui(t, tasks.size());
        storage.save(tasks);
        return reply;
    }
}
