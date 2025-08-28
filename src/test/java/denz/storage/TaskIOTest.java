package denz.storage;

import denz.model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class TaskIOTest {
    @Test
    void todo_roundTrip() {
        Todo t = new Todo("read");
        String line = TaskIO.toSaveLine(t);
        Task back = TaskIO.fromSaveLine(line);
        assertEquals(t.toString(), back.toString());
    }

    @Test
    void deadline_roundTrip() {
        Deadline d = new Deadline("return", LocalDateTime.of(2019,12,10,14,0));
        String line = TaskIO.toSaveLine(d);
        Task back = TaskIO.fromSaveLine(line);
        assertEquals(d.toString(), back.toString());
    }
}
