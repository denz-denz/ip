package denz.command;

import denz.exception.AddException;
import denz.model.TaskList;
import denz.model.Event;
import denz.model.Task;
import denz.storage.Storage;
import denz.ui.Ui;
import java.time.LocalDateTime;

public class AddEventCommand extends Command {
    private final String description;
    private final LocalDateTime start;
    private final LocalDateTime end;
    public AddEventCommand(String description, LocalDateTime start, LocalDateTime end) throws AddException {
        if (description == null || description.isBlank()) {
            throw new AddException("wthelly, you're trolling me right. You are missing the task description");
        }
        this.description = description.trim();
        this.start = start;
        this.end = end;
        if (!end.isAfter(start)) throw new AddException("denz.model.Event end must be after start.");
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AddException {
        Task t = new Event(description, start, end);
        tasks.add(t);
        ui.showTaskAdded(t, tasks.size());
        storage.save(tasks);
    }
}
