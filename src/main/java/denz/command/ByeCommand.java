package denz.command;

import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

public class ByeCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }
    @Override
    public boolean isExit() {
        return true;
    }
}
