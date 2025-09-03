package barcelona;

import barcelona.exception.FileCorruptedException;
import barcelona.parser.Parser;
import barcelona.storage.Storage;
import barcelona.ui.Ui;
import barcelona.task.TaskList;

import java.util.Scanner;

public class Barcelona {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public void run() {
        ui.greet();
        Parser parser = new Parser(ui);
        parser.listen(new Scanner(System.in), this.tasks, storage);
        ui.exit();
    }
}

