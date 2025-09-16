package barcelona.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import barcelona.exception.FileCorruptedException;
import barcelona.task.Deadlines;
import barcelona.task.Events;
import barcelona.task.Task;
import barcelona.task.Todos;
import barcelona.ui.Ui;

/**
 * Handles storage of tasklist on system hard disk
 */
public class Storage {
    private final String filePath;
    private final Ui ui;

    /**
     * Creates file storage object
     * @param filePath - path to saved txt file
     * @param ui - logger to log any errors
     */
    public Storage(String filePath, Ui ui) {
        this.filePath = filePath;
        this.ui = ui;
    }

    /**
     * Loads all tasks from the storage file into memory.
     * <p>
     * This method ensures that the storage file and its parent directory exist
     * (creating them if necessary). It then reads each line from the file, parses it
     * into the correct {@link Task} type (e.g., {@link Todos}, {@link Deadlines}, {@link Events}),
     * and returns the tasks as a list.
     * <p>
     * Corrupted lines (e.g., invalid format or invalid dates) are logged and skipped,
     * but valid tasks continue to be loaded.
     *
     * @return an {@link ArrayList} containing all successfully loaded tasks
     * @throws FileCorruptedException if the file or directory cannot be created,
     *                                or if the file cannot be accessed
     */
    public ArrayList<Task> load() throws FileCorruptedException {
        ensureFileAndDirectoryExist();
        ArrayList<Task> taskList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int lineNum = 1;
            for (String line; (line = br.readLine()) != null; lineNum++) {
                Task task = parseLine(line, lineNum);
                if (task != null) {
                    taskList.add(task);
                }
            }
        } catch (IOException e) {
            throw new FileCorruptedException("file not found");
        }

        return taskList;
    }

    /**
     * Ensures that the file and its parent directory exist,
     * creating them if necessary.
     */
    private void ensureFileAndDirectoryExist() throws FileCorruptedException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        if (!parentDir.exists() && !parentDir.mkdir()) {
            throw new FileCorruptedException("could not create directory");
        }
        try {
            if (!file.exists() && !file.createNewFile()) {
                throw new FileCorruptedException("could not create new file");
            }
        } catch (IOException e) {
            throw new FileCorruptedException("could not create new file");
        }
    }

    /**
     * Parses a single line into a Task object.
     *
     * @param line the line from the file
     * @param lineNum the line number (for logging and errors)
     * @return the parsed Task object, or null if the line is corrupted
     * @throws FileCorruptedException if data is irreparably corrupted
     */
    private Task parseLine(String line, int lineNum) throws FileCorruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        String[] params = line.split(" \\| ");
        try {
            Task task = switch (params[0]) {
            case "T" -> {
                if (params.length != 3) {
                    throw new FileCorruptedException("Corrupted todo format");
                }
                yield new Todos(params[2]);
            }
            case "D" -> {
                if (params.length != 4) {
                    throw new FileCorruptedException("Corrupted deadline format");
                }
                yield new Deadlines(LocalDateTime.parse(params[3], formatter), params[2]);
            }
            case "E" -> {
                if (params.length != 5) {
                    throw new FileCorruptedException("Corrupted event format");
                }
                yield new Events(params[2],
                        LocalDateTime.parse(params[3], formatter),
                        LocalDateTime.parse(params[4], formatter));
            }
            default -> throw new FileCorruptedException("Invalid task type");
            };

            if ("1".equals(params[1])) {
                task.markAsDone();
            }

            ui.log("INFO[" + lineNum + "] is successfully loaded");
            return task;

        } catch (DateTimeParseException e) {
            throw new FileCorruptedException("INFO[" + lineNum + "] contains invalid datetime");
        } catch (FileCorruptedException e) {
            ui.log("INFO[" + lineNum + "] is corrupted");
            return null; //skip this task, keep reading others
        }
    }
    /**
     * Function writes the updated tasklist to the txt file
     * <p>This function writes the updated contents of the tasklist
     * to the txt file so that it can be fetched in a future session</p>
     * @param tasklist - Tasklist that will be written to the file
     */
    public void write(ArrayList<Task> tasklist) {
        // Update txt file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < tasklist.size(); i++) {
                bw.write(tasklist.get(i).export());
                if (i != tasklist.size() - 1) {
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            ui.log("error writing to file");
        }
    }
}
