package denz.command;

import denz.exception.DenzException;
import denz.model.TaskList;
import denz.model.Task;
import denz.storage.Storage;
import denz.ui.Ui;

public class DeleteCommand extends Command {
    private final int oneBased;
    public DeleteCommand(int oneBased) {
        this.oneBased = oneBased;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DenzException {
        Task removed = tasks.delete(oneBased);
        ui.showRemoved(removed, tasks.size());
        storage.save(tasks);
    }
}
