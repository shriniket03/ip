package barcelona.storage;

import barcelona.task.Deadlines;
import barcelona.task.Events;
import barcelona.task.Task;
import barcelona.task.Todos;
import barcelona.ui.Ui;
import barcelona.exception.FileCorruptedException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Storage {
    public String filePath;
    public Ui ui;

    public Storage(String filePath, Ui ui) {
        this.filePath = filePath;
        this.ui = ui;
    }

    /**
     * Loads contents of txt file into the Tasklist for amendments as user gives input
     * <p>This method will read the file existing in the filePath of the storage object
     * and return a TaskList that contains all stored tasks in the txt file</p>
     * @return Fully populated tasklist after reading input from txt file
     * @throws FileCorruptedException If txt file is not formatted correctly
     */
    public ArrayList<Task> load() throws FileCorruptedException {
        ArrayList<Task> taskList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            int lineNum = 1;
            for (String line; (line = br.readLine()) != null; lineNum++) {
                String[] params = line.split(" \\| ");
                try {
                    Task toAdd;
                    if (params[0].equals("T") && params.length==3) {
                        toAdd = new Todos(params[2]);
                    } else if (params[0].equals("D") && params.length == 4) {
                        toAdd = new Deadlines(LocalDateTime.parse(params[3], formatter), params[2]);
                    } else if (params[0].equals("E") && params.length == 5) {
                        toAdd = new Events(params[2], LocalDateTime.parse(params[3], formatter),
                                LocalDateTime.parse(params[4], formatter));
                    } else {
                        throw new FileCorruptedException("File is corrupted");
                    }
                    if (params[1].equals("1")) {
                        toAdd.markAsDone();
                    }
                    taskList.add(toAdd);
                    ui.log("INFO[" + lineNum + "] is successfully loaded");
                } catch (DateTimeParseException e) {
                    throw new FileCorruptedException("INFO[" + lineNum + "] is corrupted");
                } catch (FileCorruptedException e) {
                    ui.log("INFO[" + lineNum + "] is corrupted");
                }
            }
        } catch (IOException e) {
            throw new FileCorruptedException("file not found");
        }
        return taskList;
    }

    /**
     * Function writes the updated tasklist to the txt file
     * <p>This function writes the updated contents of the tasklist
     * to the txt file so that it can be fetched in a future session</p>
     * @param arr - Tasklist that will be written to the file
     */
    public void write(ArrayList<Task> arr) {
        // Update txt file
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (int i=0; i<arr.size(); i++) {
                bw.write(arr.get(i).export());
                if (i != arr.size() - 1) {
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            ui.log("error writing to file");
        }
    }
}
