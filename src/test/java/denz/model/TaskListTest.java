package denz.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    /** Normalize to LF so we don't fail on CRLF vs LF. */
    private static String lf(String s) {
        return s.replace("\r\n", "\n");
    }

    @Test
    public void size_initiallyZero() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());
    }

    @Test
    void add_increasesSize_andRenderMatches() {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));
        list.add(new Todo("buy milk"));

        assertEquals(2, list.size());

        // TaskList.displayList() starts with a leading '\n' and uses '\n' between lines.
        String expected = "\n1. [T] [ ] read book"
                        + "\n2. [T] [ ] buy milk";

        assertEquals(lf(expected), lf(list.displayList()));
    }

    @Test
    void mark_thenUnmark_togglesDone() throws Exception {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));

        // mark
        list.mark(1);
        assertTrue(list.get(1).isDone());
        assertEquals("[T] [X] read book", list.get(1).toString());

        // unmark
        list.unmark(1);
        assertFalse(list.get(1).isDone());
        assertEquals("[T] [ ] read book", list.get(1).toString());
    }

    @Test
    void mark_outOfRange_throwsMarkException() {
        TaskList list = new TaskList();
        list.add(new Todo("only one"));
        assertThrows(denz.exception.MarkException.class, () -> list.mark(0));
        assertThrows(denz.exception.MarkException.class, () -> list.mark(2));
    }

    @Test
    void mark_alreadyDone_throwsMarkException() throws Exception {
        TaskList list = new TaskList();
        list.add(new Todo("task"));
        list.mark(1);
        assertThrows(denz.exception.MarkException.class, () -> list.mark(1));
    }

    @Test
    void unmark_notDone_throwsMarkException() {
        TaskList list = new TaskList();
        list.add(new Todo("task"));
        assertThrows(denz.exception.MarkException.class, () -> list.unmark(1));
    }

    @Test
    void delete_removesCorrectTask_andSizeDecreases() throws Exception {
        TaskList list = new TaskList();
        list.add(new Todo("A"));
        list.add(new Todo("B"));
        list.add(new Todo("C"));

        Task removed = list.delete(2); // remove "B"
        assertEquals("[T] [ ] B", removed.toString());
        assertEquals(2, list.size());

        // displayList() has leading '\n'
        String expected = "\n1. [T] [ ] A"
                        + "\n2. [T] [ ] C";
        assertEquals(lf(expected), lf(list.displayList()));
    }

    @Test
    void delete_outOfRange_throwsDeleteException() {
        TaskList list = new TaskList();
        list.add(new Todo("only one"));
        assertThrows(denz.exception.DeleteException.class, () -> list.delete(0));
        assertThrows(denz.exception.DeleteException.class, () -> list.delete(2));
    }

    @Test
    void get_invalidIndex_throwsDenzException() {
        TaskList list = new TaskList();
        list.add(new Todo("x"));
        assertThrows(denz.exception.DenzException.class, () -> list.get(0));
        assertThrows(denz.exception.DenzException.class, () -> list.get(2));
    }

    @Test
    void find_exact_keyword_success() {
        TaskList list = new TaskList();
        Task t = new Todo("gym");
        list.add(t);
        List<Task> expected = new ArrayList<>();
        expected.add(t);
        assertEquals(expected, list.find("gym"));
    }

    @Test
    void find_prefix_keyword_success() {
        TaskList list = new TaskList();
        Task t = new Todo("gym");
        list.add(t);
        List<Task> expected = new ArrayList<>();
        expected.add(t);
        assertEquals(expected, list.find("gy"));
    }

    @Test
    void find_keyword_failure() {
        TaskList list = new TaskList();
        list.add(new Todo("gym"));
        assertEquals(new ArrayList<>(), list.find("no such task"));
    }
}
