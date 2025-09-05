package barcelona;

import java.util.Scanner;

import barcelona.exception.FileCorruptedException;
import barcelona.parser.Parser;
import barcelona.storage.Storage;
import barcelona.task.TaskList;
import barcelona.ui.Ui;

/**
 * Main chatbot instance class
 */
public class Barcelona {
    private final Storage storage;
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
        this.parser = new Parser(ui);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileCorruptedException e) {
            ui.log("Error loading file");
            tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        new Barcelona("./data/duke.txt").run();
    }

    /**
     * Main chatbot run method - for console version
     */
    public void run() {
        ui.greet();
        parser.listen(new Scanner(System.in), this.tasks, storage);
        ui.exit();
    }

    public String getResponse(String input) {
        return parser.reply(input, this.tasks, storage);
    }
}

