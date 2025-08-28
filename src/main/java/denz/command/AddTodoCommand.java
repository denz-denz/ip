package denz.command;

import denz.exception.AddException;
import denz.model.TaskList;
import denz.model.Task;
import denz.model.Todo;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command to add a {@link denz.model.Todo} task to the task list.
 * <p>
 * A todo task consists of only a description.
 */
public class AddTodoCommand extends Command {
    private final String description;

    /**
     * Creates a new {@code AddTodoCommand}.
     *
     * @param description Description of the todo task.
     * @throws AddException If the description is null or blank.
     */
    public AddTodoCommand(String description) throws AddException {
        if (description == null || description.isBlank()) {
            throw new AddException("wthelly, you're trolling me right. You are missing the task description");
        }
        this.description = description.trim();
    }

    /**
     * Executes the command by creating a new {@link denz.model.Todo} and adding it to the task list.
     * The new task is also saved to persistent storage.
     *
     * @param tasks   Task list to add the new todo to.
     * @param ui      User interface to display feedback.
     * @param storage Storage to save the updated task list.
     * @throws AddException If the todo description is invalid.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws AddException {
        Task t = new Todo(description);
        tasks.add(t);
        ui.showTaskAdded(t, tasks.size());
        storage.save(tasks);
    }
}