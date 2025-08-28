package denz.storage;

import denz.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    @Test
    void load_then_save_roundTrip(@TempDir Path tmp) {
        Path file = tmp.resolve("data/denz.txt");
        Storage storage = new Storage(file.toString());

        TaskList list = new TaskList();
        list.add(new Todo("A"));
        list.add(new Todo("B"));

        storage.save(list);

        TaskList loaded = storage.load();
        assertEquals(list.size(), loaded.size());
        assertEquals(list.render(), loaded.render());
    }

    @Test
    void load_whenFileMissing_returnsEmpty(@TempDir Path tmp) {
        Path file = tmp.resolve("data/missing.txt");
        Storage storage = new Storage(file.toString());
        TaskList loaded = storage.load();
        assertEquals(0, loaded.size());
    }
}
