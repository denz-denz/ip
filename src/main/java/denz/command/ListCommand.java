package denz.command;

import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command to display all tasks in the task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by showing all tasks in the {@link TaskList}
     * using the provided {@link Ui}. The {@link Storage} is not modified.
     *
     * @param tasks   the task list to display
     * @param ui      the user interface to show the list
     * @param storage the storage handler (not used in this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.show(tasks.render());
    }
}
