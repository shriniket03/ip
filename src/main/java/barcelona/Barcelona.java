package barcelona;

import java.util.Scanner;

import barcelona.exception.FileCorruptedException;
import barcelona.parser.Parser;
import barcelona.storage.Storage;
import barcelona.task.TaskList;
import barcelona.ui.Ui;

/**
 * Main chatbot class
 */
public class Barcelona {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructor for chatbot
     * @param filepath - filepath to saved tasklist memory
     */
    public Barcelona(String filepath) {
        this.ui = new Ui();
        this.storage = new Storage(filepath, ui);
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
     * Main method to run the chatbot
     */
    public void run() {
        ui.greet();
        Parser parser = new Parser(ui);
        parser.listen(new Scanner(System.in), this.tasks, storage);
        ui.exit();
    }
}

