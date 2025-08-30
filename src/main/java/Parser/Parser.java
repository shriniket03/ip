package Parser;

import Task.*;
import Ui.Ui;
import Storage.Storage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Parser {
    enum Command {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }

    public Ui ui;

    public Parser(Ui ui) {
        this.ui = ui;
    }

    public void listen(Scanner sc, TaskList tasklist, Storage storage) {
        // default date time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        outer:
        while (true) {
            String input = sc.nextLine();
            Command action;
            try {
                action = Command.valueOf(input.split(" ")[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                ui.log("OOPS!!! I'm sorry, but I don't know what that means :-(");
                continue;
            }
            String[] params;
            switch (action) {
                case BYE:
                    break outer;
                case LIST:
                    ui.log(tasklist.list());
                    break;
                case MARK:
                    params = input.split(" ");
                    if (params.length > 1) {
                        try {
                            int taskId = Integer.parseInt(params[1]);
                            tasklist.markDone(taskId - 1);
                            ui.log("Nice! I've marked this task as done: \n"
                                    + tasklist.getTask(taskId - 1));
                        } catch (NumberFormatException e) {
                            ui.log("OOPS!!! The entered task index is not a number.");
                        } catch (NullPointerException | IndexOutOfBoundsException err) {
                            ui.log("OOPS!!! Task.Task does not exist");
                        }
                    } else {
                        ui.log("OOPS!!! The task index cannot be empty.");
                    }
                    break;
                case UNMARK:
                    params = input.split(" ");
                    if (params.length > 1) {
                        try {
                            int taskId = Integer.parseInt(params[1]);
                            tasklist.markUndone(taskId - 1);
                            ui.log("OK, I've marked this task as not done yet: \n"
                                    + tasklist.getTask(taskId - 1));
                        } catch (NumberFormatException e) {
                            ui.log("OOPS!!! The entered task index is not a number.");
                        } catch (NullPointerException | IndexOutOfBoundsException err) {
                            ui.log("OOPS!!! Task.Task does not exist");
                        }
                    } else {
                        ui.log("OOPS!!! The task index cannot be empty.");
                    }
                    break;
                case TODO:
                    params = input.split(" ");
                    if (params.length > 1) {
                        Todos todo = new Todos(params[1]);
                        int size = tasklist.add(todo);
                        ui.log("Got it. I've added this task:\n" + todo + "\n" +
                                "Now you have " + size + " tasks in the list");
                    } else {
                        ui.log("OOPS!!! The description of a todo cannot be empty.");
                    }
                    break;
                case DEADLINE:
                    params = input.split(" ", 2);
                    if (params.length > 1 && params[1].contains("/by ")) {
                        String[] subParam = params[1].split("/by ");
                        try {
                            LocalDateTime dueDate = LocalDateTime.parse(subParam[1], formatter);
                            Deadlines deadline = new Deadlines(dueDate, subParam[0]);
                            int size = tasklist.add(deadline);
                            ui.log("Got it. I've added this task:\n" + deadline + "\n" +
                                    "Now you have " + size + " tasks in the list\n");
                        } catch (DateTimeParseException e) {
                            ui.log("Invalid date/time provided");
                        }
                    } else {
                        ui.log("OOPS!!! The description/due date of a deadline cannot be empty.");
                    }
                    break;
                case EVENT:
                    params = input.split(" ", 2);
                    if (params.length > 1 && params[1].contains("/from ") && params[1].contains("/to ")) {
                        String[] split = params[1].split("/from ");
                        String description = split[0];
                        String[] subParam = split[1].split(" /to ");
                        try {
                            LocalDateTime start = LocalDateTime.parse(subParam[0], formatter);
                            LocalDateTime end = LocalDateTime.parse(subParam[1], formatter);

                            Events event = new Events(description, start, end);
                            int size = tasklist.add(event);
                            ui.log("Got it. I've added this task:\n" + event + "\n" +
                                    "Now you have " + size + " tasks in the list");
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date/time provided");
                        }
                    } else {
                        ui.log("OOPS!!! The description/start date/end date of an event cannot be empty.");
                    }
                    break;
                case DELETE:
                    params = input.split(" ");
                    if (params.length > 1) {
                        try {
                            int taskId = Integer.parseInt(params[1]);
                            Task toRemove = tasklist.getTask(taskId - 1);
                            int size = tasklist.remove(toRemove);
                            ui.log("Noted. I've removed this task: " + toRemove
                                    + "\n" + "Now you have " + size + " tasks in the list");
                        } catch (NumberFormatException e) {
                            ui.log("OOPS!!! The entered task index is not a number.");
                        } catch (NullPointerException | IndexOutOfBoundsException err) {
                            ui.log("OOPS!!! Task.Task does not exist");
                        }
                    } else {
                        ui.log("OOPS!!! The task index cannot be empty.");
                    }
                    break;
            }
            storage.write(tasklist.getList());
        }
    }
}
