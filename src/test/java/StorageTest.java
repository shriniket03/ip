import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import barcelona.storage.Storage;
import barcelona.ui.Ui;

public class StorageTest {
    @Test
    public void load_normal_tasklist_file_test() throws Exception {
        // loading a normal file results in a proper array being returned
        assertEquals(8, new Storage("./test/storageTest1.txt", new Ui()).load().size());
    }

    @Test
    public void load_corrupted_tasklist_file_test() throws Exception {
        // loading a file with some corrupted lines results in only the normal lines being fetched in
        assertEquals(1, new Storage("./test/storageTest2.txt", new Ui()).load().size());
    }

    @Test
    public void load_non_existent_tasklist_file_test() throws Exception {
        // loading a file that does not exist creates an empty file & returns an empty array
        assertEquals(0, new Storage("./test/storageTest3.txt", new Ui()).load().size());
    }
}
