package barcelona;

import java.util.ArrayList;

import barcelona.exception.FileCorruptedException;
import barcelona.parser.Parser;
import barcelona.storage.Storage;
import barcelona.task.Task;
import barcelona.task.TaskList;
import barcelona.ui.Ui;

/**
 * Entry point for the Barcelona chatbot application.
 * <p>
 * The {@code Barcelona} class provides an interactive task management chatbot
 * that allows users to create, update, search, and delete tasks. Tasks are
 * persisted in storage and managed in memory using a {@link TaskList}.
 * </p>
 *
 * <h2>Supported Task Types</h2>
 * <ul>
 *   <li>{@link barcelona.task.Todos} – a simple task with a description.</li>
 *   <li>{@link barcelona.task.Deadlines} – a task with a deadline.</li>
 *   <li>{@link barcelona.task.Events} – a task scheduled between two dates/times.</li>
 * </ul>
 *
 * <h2>Core Features</h2>
 * <ul>
 *   <li>Maintain a list of tasks in memory with file persistence support.</li>
 *   <li>Add new tasks of type Todo, Deadline, or Event.</li>
 *   <li>Mark or unmark tasks as done.</li>
 *   <li>Delete tasks when no longer needed.</li>
 *   <li>Search for tasks by keyword.</li>
 *   <li>List all tasks currently stored.</li>
 *   <li>Change the storage directory to load or save tasks from a different file.</li>
 * </ul>
 *
 * <h2>Supported Commands</h2>
 * <ul>
 *   <li><b>BYE</b> – Exit the application.</li>
 *   <li><b>LIST</b> – Display all tasks in the current {@link TaskList}.</li>
 *   <li><b>MARK &lt;index&gt;</b> – Mark the task at the specified index as done.</li>
 *   <li><b>UNMARK &lt;index&gt;</b> – Mark the task at the specified index as not done.</li>
 *   <li><b>TODO &lt;description&gt;</b> – Create a {@link barcelona.task.Todos} task.</li>
 *   <li><b>DEADLINE &lt;description&gt; /by &lt;due date&gt;</b> – Create a {@link barcelona.task.Deadlines} task.</li>
 *   <li><b>EVENT &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;</b> –
 *       Create a {@link barcelona.task.Events} task.</li>
 *   <li><b>DELETE &lt;index&gt;</b> – Remove the task at the specified index.</li>
 *   <li><b>FIND &lt;keyword&gt;</b> – Search for tasks containing the given keyword.</li>
 * </ul>
 */
public class Barcelona {
    private Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /**
     * Creates a chatbot instance using the specified file path for task storage.
     * <p>
     * If the file exists and is correctly formatted, tasks will be loaded into memory.
     * If the file is missing or corrupted, a new empty {@link TaskList} will be created.
     * </p>
     *
     * @param filepath path to the file used for task persistence
     */
    public Barcelona(String filepath) {
        this.ui = new Ui();
        this.storage = new Storage(filepath, ui);
        this.parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (FileCorruptedException e) {
            ui.log("Error loading file");
            tasks = new TaskList();
        }
    }

    /**
     * Changes the storage directory for task persistence.
     * <p>
     * If the provided file path is valid and contains a properly formatted task list,
     * the existing storage will be updated and tasks will be reloaded. If the file
     * is invalid or corrupted, the current storage and task list remain unchanged.
     * </p>
     *
     * @param filepath new file path for task storage
     * @return chatbot response message after attempting to load the new file
     */
    public String changeDirectory(String filepath) {
        Storage storage = new Storage(filepath, ui);
        try {
            ArrayList<Task> newTaskList = storage.load();
            this.storage = storage;
            this.tasks = new TaskList(newTaskList);
            return "Successfully loaded new file. Your tasks:\n" + tasks.list();
        } catch (FileCorruptedException e) {
            return "Error loading file";
        }
    }

    /**
     * Processes user input and generates a chatbot response.
     * <p>
     * This method is used by the GUI version to provide conversational responses
     * to user commands. The input is parsed by the {@link Parser} and executed
     * against the current {@link TaskList}.
     * </p>
     *
     * @param input raw user input as a string
     * @return a response string to be displayed in the chatbot UI
     */
    public String getResponse(String input) {
        return parser.reply(input, this.tasks, storage);
    }
}
