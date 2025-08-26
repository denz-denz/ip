package denz.command;

import denz.exception.AddException;
import denz.model.TaskList;
import denz.model.Task;
import denz.model.Todo;
import denz.storage.Storage;
import denz.ui.Ui;

public class AddTodoCommand extends Command {
    private final String description;
    public AddTodoCommand(String description) throws AddException {
        if (description == null || description.isBlank()) {
            throw new AddException("wthelly, you're trolling me right. You are missing the task description");
        }
        this.description = description.trim();
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AddException {
        Task t = new Todo(description);
        tasks.add(t);
        ui.showTaskAdded(t, tasks.size());
        storage.save(tasks);
    }
}
