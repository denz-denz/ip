package denz.model;

import denz.exception.DeleteException;
import denz.exception.DenzException;
import denz.exception.MarkException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class TaskListTest {

    @Test
    public void size_initiallyZero() {
        TaskList list = new TaskList();
        assertEquals(0,list.size());
    }

    @Test
    void add_increasesSize_andRenderMatches() {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));
        list.add(new Todo("buy milk"));

        assertEquals(2, list.size());

        String expected =
                "Here is everything you have on your plate :(\n" +
                        "1. [T] [ ] read book\n" +
                        "2. [T] [ ] buy milk";
        assertEquals(expected, list.render());
    }

    @Test
    void mark_thenUnmark_togglesDone() throws Exception {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));

        //mark
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

        String expected =
                "Here is everything you have on your plate :(\n" +
                        "1. [T] [ ] A\n" +
                        "2. [T] [ ] C";
        assertEquals(expected, list.render());
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
        List<Task> dummy = new ArrayList<>();
        dummy.add(t);
        assertEquals(list.find("gym"), dummy);
    }

    @Test
    void find_prefix_keyword_success() {
        TaskList list = new TaskList();
        Task t = new Todo("gym");
        list.add(t);
        List<Task> dummy = new ArrayList<>();
        dummy.add(t);
        assertEquals(list.find("gy"), dummy);
    }

    @Test
    void find_keyword_failure() {
        TaskList list = new TaskList();
        Task t = new Todo("gym");
        list.add(t);
        assertEquals(list.find("no such task"), new ArrayList<>());
    }
}
