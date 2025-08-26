package denz.command;

import denz.model.TaskList;
import denz.model.Deadline;
import denz.storage.Storage;
import denz.ui.Ui;
import java.time.LocalDateTime;
import denz.exception.AddException;
import denz.model.Task;

public class AddDeadlineCommand extends Command {
    private final String description;
    private final LocalDateTime by;
    public AddDeadlineCommand(String description, LocalDateTime by) throws AddException {
        if (description == null || description.isBlank()) {
            throw new AddException("wthelly, you're trolling me right. You are missing the task description");
        }
        this.description = description.trim();
        this.by = by;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AddException {
        Task t = new Deadline(description, by);
        tasks.add(t);
        ui.showTaskAdded(t, tasks.size());
        storage.save(tasks);
    }
}
