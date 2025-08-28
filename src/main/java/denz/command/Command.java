package denz.command;

import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;
import denz.exception.DenzException;

/**
 * Represents an abstract command in the application.
 * <p>
 * All concrete command classes (e.g., {@code AddTodoCommand},
 * {@code ByeCommand}) must extend this class and implement
 * the {@link #execute(TaskList, Ui, Storage)} method.
 */
public abstract class Command {

    /**
     * Executes the command with the given context.
     *
     * @param tasks   the task list to operate on
     * @param ui      the user interface for displaying output
     * @param storage the storage handler for saving/loading tasks
     * @throws DenzException if an error occurs while executing the command
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws DenzException;

    /**
     * Determines whether this command signals the application to exit.
     *
     * @return {@code true} if the application should exit after this command,
     *         {@code false} otherwise
     */
    public boolean isExit() {
        return false;
    }
}
