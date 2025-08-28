package denz.command;

import denz.exception.DenzException;
import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command that marks a task as completed in the task list.
 */
public class MarkCommand extends Command {
    private final int oneBased;

    /**
     * Creates a {@code MarkCommand} to mark the specified task.
     *
     * @param oneBased the one-based index of the task to mark
     */
    public MarkCommand(int oneBased) {
        this.oneBased = oneBased;
    }

    /**
     * Executes the mark command by marking the specified task as done.
     * Displays the result using the {@link Ui}, and saves the updated task list to {@link Storage}.
     * If the index is invalid or the task is already marked, an error message is shown.
     *
     * @param tasks   the task list containing the tasks
     * @param ui      the user interface to display results
     * @param storage the storage handler to persist changes
     * @throws DenzException if the marking fails due to invalid index or other task-related errors
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DenzException {
        try {
            tasks.mark(oneBased);
            ui.showMark(tasks.get(oneBased));
            storage.save(tasks);
        } catch (DenzException e) {
            ui.showError(e.getMessage());
        }
    }
}
