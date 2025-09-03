import barcelona.storage.Storage;
import barcelona.task.Task;
import barcelona.task.TaskList;
import barcelona.task.Todos;
import barcelona.ui.Ui;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskListTest {
    @Test
    public void create_empty_tasklist() throws Exception {
        // create an empty tasklist
        assertEquals(0, new TaskList().getList().size());
    }

    @Test
    public void create_tasklist_from_file() throws Exception {
        // create a tasklist from file
        assertEquals(8, new TaskList(new Storage("./test/storageTest1.txt",
                new Ui()).load()).getList().size());
    }

    @Test
    public void add_task_to_tasklist() throws Exception {
        // create a tasklist from file
        TaskList list = new TaskList(new Storage("./test/storageTest1.txt",
                new Ui()).load());
        assertEquals(8, list.getList().size());
        list.add(new Todos("New Todo!"));
        assertEquals(9, list.getList().size());
    }

    @Test
    public void remove_task_from_tasklist() throws Exception {
        // create a tasklist from file
        TaskList list = new TaskList(new Storage("./test/storageTest1.txt",
                new Ui()).load());
        assertEquals(8, list.getList().size());
        Task toRemove = list.getTask(0);
        list.remove(toRemove);
        assertFalse(list.getList().contains(toRemove));
    }
}
