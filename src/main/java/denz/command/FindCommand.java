package denz.command;

import java.util.List;

import denz.model.Task;
import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Represents a command that searches for tasks whose descriptions
 * contain a specified keyword and displays the matches.
 */
public class FindCommand extends Command {
    /** Keyword used to filter tasks by description. */
    private final String[] keywords;

    /**
     * Constructs a {@code FindCommand} with the given keyword.
     *
     * @param keywords the search term to look for in task descriptions
     */
    public FindCommand(String ... keywords) {
        this.keywords = keywords;
    }

    /**
     * Executes the find operation: queries the {@link TaskList} for tasks
     * containing the keyword, and displays the results via {@link Ui}.
     * This command does not modify {@link Storage}.
     *
     * @param tasks   the task list to search
     * @param ui      the user interface used to display output
     * @param storage the storage handler (not used by this command)
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        List<Task> matches = tasks.find(keywords);
        if (matches.isEmpty()) {
            ui.show("No matching tasks found for: " + keywords);
            return;
        }
        ui.show(tasks.displayList(matches));
    }

    @Override
    public String executeGui(TaskList tasks, Ui ui, Storage storage) {
        List<Task> matches = tasks.find(keywords);
        String reply;
        if (matches.isEmpty()) {
            reply = ui.showGui("No matching tasks found for: " + varArgsToString(keywords));
            return reply;
        }
        reply = ui.showGui(tasks.displayList(matches));
        return reply;
    }
    /**
     * Converts a string of varArgs into appropriate String of all keywords
     *
     * @param keywords   the keywords user pass in
     * @return The String denz chatbot will output to show what keywords user has typed in
     */
    public String varArgsToString(String ... keywords) {
        int length = keywords.length;
        String res = "";
        for (int i = 0; i < length; i++) {
            res += keywords[i].toString() + " ";
        }
        return res;
    }
}
