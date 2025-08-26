package denz.command;

import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

public class NoOpCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        return;
    }
}
