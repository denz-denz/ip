package denz.command;

import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a no-operation command.
 * <p>
 * This command does nothing and is typically used
 * to handle empty user inputs gracefully.
 */
public class NoOpCommand extends Command {

    /**
     * Executes the no-operation command.
     * <p>
     * This implementation does nothing.
     *
     * @param tasks   the task list (unused)
     * @param ui      the user interface (unused)
     * @param storage the storage handler (unused)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        // Intentionally left blank: no operation performed.
    }

    @Override
    public String executeGui(TaskList tasks, Ui ui, Storage storage) {
        // Intentionally left blank: no operation performed.
        return "No operation!";
    }
}
