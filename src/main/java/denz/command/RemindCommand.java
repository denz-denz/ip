package denz.command;

import java.util.List;

import denz.model.Task;
import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command that reminds the user about upcoming tasks
 * within a specified number of days.
 * <p>
 * This command checks all tasks in the {@link TaskList} and filters out
 * those with deadlines or events that fall within the given limit.
 * The results are displayed via the {@link Ui}.
 * <ul>
 *     <li>If no limit is provided, a default of 10 days is used.</li>
 *     <li>If no tasks are found within the range, a message informs the user.</li>
 * </ul>
 * This command does not modify {@link Storage}.
 *
 * @author aldenchua
 * @since 9/9/25
 */
public class RemindCommand extends Command {
    /** Number of days to check for upcoming tasks. */
    private int limit;

    /**
     * Constructs a {@code RemindCommand} with a user-specified limit.
     *
     * @param limit number of days ahead to search for tasks
     */
    public RemindCommand(int limit) {
        this.limit = limit;
    }

    /**
     * Constructs a {@code RemindCommand} with a default limit of 10 days.
     */
    public RemindCommand() {
        this.limit = 10; // default 10 days
    }

    /**
     * Executes the reminder check in CLI mode.
     *
     * @param tasks   the task list to search
     * @param ui      the user interface to show results
     * @param storage the storage handler (not used by this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> reminderTaskLists = tasks.remind(limit);
        if (reminderTaskLists.isEmpty()) {
            ui.show("You lazy bum, have nothing coming up in the next " + limit + " days.");
        } else {
            ui.show(tasks.displayList(reminderTaskLists));
        }
    }

    /**
     * Executes the reminder check in GUI mode.
     *
     * @param tasks   the task list to search
     * @param ui      the user interface to show results
     * @param storage the storage handler (not used by this command)
     * @return the response string to be displayed in the GUI
     */
    @Override
    public String executeGui(TaskList tasks, Ui ui, Storage storage) {
        List<Task> reminderTaskLists = tasks.remind(limit);
        if (reminderTaskLists.isEmpty()) {
            return ui.showGui("You lazy bum, have nothing coming up in the next " + limit + " days.");
        }
        return ui.showGui(tasks.displayList(reminderTaskLists));
    }
}
