package denz.command;

import denz.exception.DenzException;
import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command that unmarks a task as not done.
 * <p>
 * This command updates the status of a task in the task list,
 * shows the result to the user, and saves the updated list
 * to storage.
 */
public class UnmarkCommand extends Command {
    /** The one-based index of the task to unmark. */
    private final int taskNumber;

    /**
     * Constructs an {@code UnmarkCommand}.
     *
     * @param taskNumber the one-based index of the task to unmark
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Executes the unmark command.
     * <p>
     * Attempts to unmark the task at the given index. If successful,
     * displays the result to the user and saves the task list.
     * If an error occurs (e.g., invalid index), the error message is shown.
     *
     * @param tasks   the task list containing the task to unmark
     * @param ui      the user interface used to show feedback
     * @param storage the storage handler used to persist changes
     * @throws DenzException if an unexpected error occurs during execution
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DenzException {
        try {
            tasks.unmark(taskNumber);
            ui.showUnmark(tasks.get(taskNumber));
            storage.save(tasks);
        } catch (DenzException e) {
            ui.showError(e.getMessage());
        }
    }

    @Override
    public String executeGui(TaskList tasks, Ui ui, Storage storage) throws DenzException {
        String reply;
        try {
            tasks.unmark(taskNumber);
            reply = ui.showUnmarkGui(tasks.get(taskNumber));
            storage.save(tasks);
        } catch (DenzException e) {
            reply = ui.showErrorGui(e.getMessage());
        }
        return reply;
    }
}
