package denz.command;

import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.show(tasks.render());
    }
}
