package denz.command;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import denz.exception.DenzException;
import denz.model.Task;
import denz.model.TaskList;
import denz.storage.Storage;
import denz.ui.Ui;

public class CommandIntegrationTest {
    static class SilentUi extends Ui {
        @Override public void showTaskAdded(Task t, int size) { }
        @Override public void showMark(Task t) { }
        @Override public void showUnmark(Task t) { }
        @Override public void showRemoved(Task t, int size) { }
    }

    @Test
    void addTodo_then_mark(@TempDir Path tmp) throws DenzException {
        Storage storage = new Storage(tmp.resolve("data/denz.txt").toString());
        TaskList tasks = new TaskList();
        Ui ui = new SilentUi();

        new AddTodoCommand("read").execute(tasks, ui, storage);
        assertEquals(1, tasks.size());
        new MarkCommand(1).execute(tasks, ui, storage);
        assertTrue(tasks.get(1).isDone());
    }
}
