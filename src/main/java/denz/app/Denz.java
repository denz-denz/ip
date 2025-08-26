package denz.app;

import denz.command.Command;
import denz.exception.DenzException;
import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;
import denz.parser.Parser;

public class Denz {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;
    public Denz(String filePath) {
        this.storage = new Storage(filePath);
        this.tasks = storage.load();
        this.ui = new Ui();
    }
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks,ui,storage);
                isExit = c.isExit();
            } catch (DenzException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }
    public static void main(String[] args) {
        new Denz("data/denz.txt").run();
    }
}

