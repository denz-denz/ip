package denz.app;

import denz.command.Command;
import denz.exception.DenzException;
import denz.model.TaskList;
import denz.parser.Parser;
import denz.storage.Storage;
import denz.ui.Ui;

/**
 * Entry point of the Denz application.
 * <p>
 * Denz is a task manager application that supports creating, listing, marking,
 * unmarking, deleting, and finding tasks. Data is persisted locally using
 * {@link Storage}.
 */
public class Denz {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Creates a new instance of the Denz application.
     *
     * @param filePath Path to the file where tasks are loaded from and saved to.
     */
    public Denz(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
        this.ui = new Ui();
    }

    /**
     * Runs the main application loop. This method:
     * <ul>
     *   <li>Displays a welcome message.</li>
     *   <li>Repeatedly reads user commands and executes them.</li>
     *   <li>Handles errors by displaying the error message.</li>
     *   <li>Stops when an exit command is issued.</li>
     * </ul>
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.executeGui(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DenzException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    /**
     * Starts the Denz application.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        new Denz("data/denz.txt").run();
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) throws DenzException {
        try {
            Command c = Parser.parse(input);
            String reply = c.executeGui(tasks, ui, storage);
            return reply;
        } catch (DenzException e) {
            return ui.showErrorGui(e.getMessage());
        }
    }
}
