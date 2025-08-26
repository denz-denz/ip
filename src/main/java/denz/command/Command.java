package denz.command;

import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;
import denz.exception.DenzException;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DenzException;
    public boolean isExit() {
        return false;
    }
}
