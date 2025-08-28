package denz.model;

import denz.exception.MarkException;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTypesTest {
    /* -------------------- TODO -------------------- */
    @Test
    void todo_toString_and_mark_unmark() throws Exception {
        Todo t = new Todo("read book");

        // initial
        assertFalse(t.isDone());
        assertEquals("[T] [ ] read book", t.toString());

        // mark
        t.mark();
        assertTrue(t.isDone());
        assertEquals("[T] [X] read book", t.toString());

        // unmark
        t.unmark();
        assertFalse(t.isDone());
        assertEquals("[T] [ ] read book", t.toString());
    }

    /* -------------------- DEADLINE -------------------- */
    @Test
    void deadline_toString_formats_and_mark_unmark() throws Exception {
        LocalDateTime due = LocalDateTime.of(2019, Month.DECEMBER, 10, 14, 0);
        Deadline d = new Deadline("return book", due);

        // uses "MMM d yyyy HH:mm"
        assertEquals("[D] [ ] return book(by: Dec 10 2019 14:00)", d.toString());

        d.mark();
        assertEquals("[D] [X] return book(by: Dec 10 2019 14:00)", d.toString());

        d.unmark();
        assertEquals("[D] [ ] return book(by: Dec 10 2019 14:00)", d.toString());
    }

    @Test
    void deadline_getter_returns_same_dueDate() {
        LocalDateTime due = LocalDateTime.of(2020, 1, 1, 12, 30);
        Deadline d = new Deadline("pay bills", due);
        assertEquals(due, d.getDueDate());
    }

    /* -------------------- EVENT -------------------- */

    @Test
    void event_toString_formats_and_mark_unmark() throws Exception {
        LocalDateTime start = LocalDateTime.of(2019, Month.DECEMBER, 10, 14, 0);
        LocalDateTime end   = LocalDateTime.of(2019, Month.DECEMBER, 10, 16, 0);
        Event e = new Event("project meeting", start, end);

        // uses "MMM d yyyy HH:mm" for both start and end
        assertEquals("[E] [ ] project meeting(from: Dec 10 2019 14:00 to: Dec 10 2019 16:00)", e.toString());

        e.mark();
        assertEquals("[E] [X] project meeting(from: Dec 10 2019 14:00 to: Dec 10 2019 16:00)", e.toString());

        e.unmark();
        assertEquals("[E] [ ] project meeting(from: Dec 10 2019 14:00 to: Dec 10 2019 16:00)", e.toString());
    }

    @Test
    void event_getters_return_same_values() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 9, 30);
        LocalDateTime end   = LocalDateTime.of(2020, 1, 1, 11, 0);
        Event e = new Event("kickoff", start, end);

        assertEquals(start, e.getStartDate());
        assertEquals(end, e.getEndDate());
    }
}
