package denz.command;

import denz.exception.DenzException;
import denz.model.TaskList;
import denz.model.Task;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    /** One-based index of the task to delete. */
    private final int oneBased;

    /**
     * Constructs a {@code DeleteCommand} with the given index.
     *
     * @param oneBased the one-based index of the task to delete
     */
    public DeleteCommand(int oneBased) {
        this.oneBased = oneBased;
    }

    /**
     * Executes the delete command by removing the specified task
     * from the {@link TaskList}, updating the {@link Storage}, and
     * showing feedback via the {@link Ui}.
     *
     * @param tasks   the task list to operate on
     * @param ui      the user interface to show output
     * @param storage the storage handler to save the updated list
     * @throws DenzException if the given index is invalid or any
     *                       other error occurs during deletion
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DenzException {
        Task removed = tasks.delete(oneBased);  // may throw DeleteException
        ui.showRemoved(removed, tasks.size());
        storage.save(tasks);
    }
}
