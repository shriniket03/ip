package barcelona;

import java.util.ArrayList;

import barcelona.exception.FileCorruptedException;
import barcelona.parser.Parser;
import barcelona.storage.Storage;
import barcelona.task.Task;
import barcelona.task.TaskList;
import barcelona.ui.Ui;

/**
 * A simple chatbot application for managing tasks.
 * <p>
 * The {@code Barcelona} provides an interactive command-line interface
 * where users can add, manage, and search for tasks. Tasks are stored
 * in a {@link TaskList}, and can be of type {@link barcelona.task.Todos},
 * {@link barcelona.task.Deadlines}, or {@link barcelona.task.Events}.
 * </p>
 *
 * <h2>Main Features</h2>
 * <ul>
 *   <li>Maintain a list of tasks in memory.</li>
 *   <li>Support for multiple task types: Todo, Deadline & Event
 *   <li>Mark tasks as done or undone.</li>
 *   <li>Delete tasks when no longer needed.</li>
 *   <li>Search tasks by keyword.</li>
 *   <li>View all current tasks in the list.</li>
 * </ul>
 *
 * <h2>Supported Commands</h2>
 * <ul>
 *   <li><b>BYE</b> – Exit the application.</li>
 *   <li><b>LIST</b> – List all tasks in the {@link TaskList}.</li>
 *   <li><b>MARK &lt;index&gt;</b> – Mark the task at the given index as done.</li>
 *   <li><b>UNMARK &lt;index&gt;</b> – Unmark (undo) the task at the given index.</li>
 *   <li><b>TODO &lt;description&gt;</b> – Create a {@link barcelona.task.Todos} task.</li>
 *   <li><b>DEADLINE &lt;description&gt; /by &lt;due date&gt;</b> – Create a {@link barcelona.task.Deadlines} task.</li>
 *   <li><b>EVENT &lt;description&gt; /from &lt;start date&gt; /to &lt;end date&gt;</b> –
 *   Create an {@link barcelona.task.Events} task.</li>
 *   <li><b>DELETE &lt;index&gt;</b> – Delete the task at the given index.</li>
 *   <li><b>FIND &lt;keyword&gt;</b> – Search for tasks containing the given keyword in their description.</li>
 * </ul>
 */
public class Barcelona {
    private Storage storage;
    private TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /**
     * Constructor for chatbot
     * @param filepath - filepath to saved tasklist memory
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
     * Change the file directory of the txt file
     * If invalid, retains original file
     * If valid, it will update the tasklist & set the write
     * location to the new file location
     * @param filepath - Filepath to the new txt file
     * @return reply to the user
     */
    public String changeDirectory(String filepath) {
        Storage storage = new Storage(filepath, ui);
        try {
            ArrayList<Task> newTaskList = storage.load();
            this.storage = storage;
            this.tasks = new TaskList(newTaskList);
            return "Successfully loaded new file. Your tasks: \n"
                    + tasks.list();
        } catch (FileCorruptedException e) {
            return "Error loading file";
        }
    }

    /**
     * Generate chatbot response - for GUI version
     * @param input - User Input as String
     * @return Chatbot response - to be displayed in GUI
     */
    public String getResponse(String input) {
        return parser.reply(input, this.tasks, storage);
    }
}

