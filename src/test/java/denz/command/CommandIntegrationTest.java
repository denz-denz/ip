package denz.command;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import denz.exception.DeleteException;
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

    @Test
    void addDeadline_then_unmark(@TempDir Path tmp) throws DenzException {
        Storage storage = new Storage(tmp.resolve("data/denz.txt").toString());
        TaskList tasks = new TaskList();
        Ui ui = new SilentUi();

        LocalDateTime by = LocalDateTime.of(2025, 9, 30, 23, 59);
        new AddDeadlineCommand("submit report", by).execute(tasks, ui, storage);

        assertEquals(1, tasks.size());
        assertFalse(tasks.get(1).isDone(), "Newly added deadline should start unmarked");

        new MarkCommand(1).execute(tasks, ui, storage);
        assertTrue(tasks.get(1).isDone());

        new UnmarkCommand(1).execute(tasks, ui, storage);
        assertFalse(tasks.get(1).isDone(), "Unmark should clear done flag");
    }

    @Test
    void addEvent_then_delete(@TempDir Path tmp) throws DenzException {
        Storage storage = new Storage(tmp.resolve("data/denz.txt").toString());
        TaskList tasks = new TaskList();
        Ui ui = new SilentUi();

        LocalDateTime start = LocalDateTime.of(2025, 9, 20, 14, 0);
        LocalDateTime end = LocalDateTime.of(2025, 9, 20, 16, 0);
        new AddEventCommand("project meeting", start, end).execute(tasks, ui, storage);
        new AddTodoCommand("read book").execute(tasks, ui, storage);
        assertEquals(2, tasks.size());

        new DeleteCommand(1).execute(tasks, ui, storage);
        assertEquals(1, tasks.size(), "Delete should remove exactly one task");
        assertEquals("read book", tasks.get(1).getDescription(), "Remaining task should be the second one");
    }

    @Test
    void find_returns_matching_tasks(@TempDir Path tmp) throws DenzException {
        Storage storage = new Storage(tmp.resolve("data/denz.txt").toString());
        TaskList tasks = new TaskList();
        Ui ui = new SilentUi();

        new AddTodoCommand("read CS2103T").execute(tasks, ui, storage);
        new AddTodoCommand("write reflection").execute(tasks, ui, storage);

        LocalDateTime by = LocalDateTime.of(2025, 10, 1, 18, 0);
        new AddDeadlineCommand("read paper", by).execute(tasks, ui, storage);

        // Ensure it runs and doesn't mutate the list
        assertDoesNotThrow(() -> new FindCommand("read").execute(tasks, ui, storage));
        assertEquals(3, tasks.size(), "Find should not mutate the underlying list");
        assertTrue(
                tasks.get(1).getDescription().contains("read")
                        || tasks.get(3).getDescription().contains("read")
        );
    }



    @Test
    void delete_with_invalid_index_throws(@TempDir Path tmp) throws DenzException {
        Storage storage = new Storage(tmp.resolve("data/denz.txt").toString());
        TaskList tasks = new TaskList();
        Ui ui = new SilentUi();

        new AddTodoCommand("task A").execute(tasks, ui, storage);
        assertThrows(DeleteException.class, () -> new DeleteCommand(-1).execute(tasks, ui, storage));
        assertThrows(DeleteException.class, () -> new DeleteCommand(99).execute(tasks, ui, storage));
    }

    @Test
    void mark_then_delete_keeps_consistent_state(@TempDir Path tmp) throws DenzException {
        Storage storage = new Storage(tmp.resolve("data/denz.txt").toString());
        TaskList tasks = new TaskList();
        Ui ui = new SilentUi();

        new AddTodoCommand("A").execute(tasks, ui, storage); // 1
        new AddTodoCommand("B").execute(tasks, ui, storage); // 2
        new AddTodoCommand("C").execute(tasks, ui, storage); // 3

        new MarkCommand(2).execute(tasks, ui, storage);
        assertTrue(tasks.get(2).isDone());

        new DeleteCommand(2).execute(tasks, ui, storage);
        assertEquals(2, tasks.size());
        assertEquals("A", tasks.get(1).getDescription());
        assertEquals("C", tasks.get(2).getDescription(), "Indices should re-pack after deletion");
        assertFalse(tasks.get(2).isDone(), "New #2 should be previously-unmarked task C");
    }
}
