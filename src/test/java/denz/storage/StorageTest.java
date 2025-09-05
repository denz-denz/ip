package denz.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import denz.model.*;

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
        assertEquals(list.formatTaskList(), loaded.formatTaskList());
    }

    @Test
    void load_whenFileMissing_returnsEmpty(@TempDir Path tmp) {
        Path file = tmp.resolve("data/missing.txt");
        Storage storage = new Storage(file.toString());
        TaskList loaded = storage.load();
        assertEquals(0, loaded.size());
    }
}
