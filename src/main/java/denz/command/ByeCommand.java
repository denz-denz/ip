package denz.command;

import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command that exits the program.
 * <p>
 * When executed, this command displays the exit message
 * and signals the application to terminate.
 */
public class ByeCommand extends Command {

    /**
     * Executes the bye command.
     * Displays the farewell message using the {@link Ui}.
     *
     * @param tasks   the task list (not used here)
     * @param ui      the user interface to display messages
     * @param storage the storage handler (not used here)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    /**
     * Checks if this command signals the application to exit.
     *
     * @return {@code true}, since bye always ends the application
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
