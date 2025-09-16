package barcelona.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import barcelona.storage.Storage;
import barcelona.task.Deadlines;
import barcelona.task.Events;
import barcelona.task.Task;
import barcelona.task.TaskList;
import barcelona.task.Todos;

/**
 * Parses and processes user input commands for the chatbot.
 */
public class Parser {
    /**
     * Supported command types.
     */
    private enum Command {
        BYE,
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE,
        FIND,
        HELP
    }

    public static final String HELP_COMMANDS = """
            User Commands:

            - list: show all tasks
            - todo <todo description>: add a to-do

            - deadline <description> /by <date-time>: add a deadline

            - event <description> /from <date-time> /to <date-time>: schedule an event

            <date-time> format is in DD/MM/YY HHmm

            - mark/unmark <task index>: track your progress
            - delete <task index>: remove a task
            - find <search keyword>: search tasks
            - bye: say goodbye""";
    /**
     * Returns the chatbot's reply based on the user input.
     *
     * @param input the user input string
     * @param taskList the current list of tasks
     * @param storage the storage instance used to persist tasks
     * @return the chatbot's response message
     */
    public String reply(String input, TaskList taskList, Storage storage) {
        assert !input.isEmpty();
        String response;
        Command action;
        try {
            action = Command.valueOf(input.split(" ")[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            return "OOPS!!! I'm sorry, but I don't know what that means :-(";
        }
        switch (action) {
        case BYE:
            response = "Bye. Hope to see you again soon!";
            break;
        case LIST:
            response = taskList.list();
            break;
        case MARK:
            response = handleMark(input, taskList);
            break;
        case UNMARK:
            response = handleUnmark(input, taskList);
            break;
        case TODO:
            response = handleCreateTodo(input, taskList);
            break;
        case DEADLINE:
            response = handleCreateDeadline(input, taskList);
            break;
        case EVENT:
            response = handleCreateEvent(input, taskList);
            break;
        case DELETE:
            response = handleDelete(input, taskList);
            break;
        case FIND:
            response = handleFind(input, taskList);
            break;
        case HELP:
            response = HELP_COMMANDS;
            break;
        default:
            response = "OOPS!!! I'm sorry, but I don't know what that means :-(";
            break;
        }
        storage.write(taskList.getList());
        return response;
    }

    /**
     * Handles marking a task as completed.
     *
     * @param input the user input
     * @param taskList the task list to modify
     * @return the chatbot response message
     */
    private String handleMark(String input, TaskList taskList) {
        String[] params = input.split(" ");
        if (params.length <= 1) {
            return "OOPS!!! The task index cannot be empty.";
        }
        try {
            int taskId = Integer.parseInt(params[1]);
            taskList.markDone(taskId - 1);
            return "Nice! I've marked this task as done:\n"
                    + taskList.getTask(taskId - 1);
        } catch (NumberFormatException e) {
            return "OOPS!!! The entered task index is not a number.";
        } catch (NullPointerException | IndexOutOfBoundsException err) {
            return "OOPS!!! Task does not exist";
        }
    }

    /**
     * Handles unmarking a task (marking it as not done).
     *
     * @param input the user input
     * @param taskList the task list to modify
     * @return the chatbot response message
     */
    private String handleUnmark(String input, TaskList taskList) {
        String[] params = input.split(" ");
        if (params.length <= 1) {
            return "OOPS!!! The task index cannot be empty.";
        }
        try {
            int taskId = Integer.parseInt(params[1]);
            taskList.markUndone(taskId - 1);
            return "OK, I've marked this task as not done yet:\n"
                    + taskList.getTask(taskId - 1);
        } catch (NumberFormatException e) {
            return "OOPS!!! The entered task index is not a number.";
        } catch (NullPointerException | IndexOutOfBoundsException err) {
            return "OOPS!!! Task does not exist";
        }
    }

    /**
     * Handles creating and adding a new Todo task.
     *
     * @param input the user input
     * @param taskList the task list to modify
     * @return the chatbot response message
     */
    private String handleCreateTodo(String input, TaskList taskList) {
        String[] params = input.split(" ", 2);
        if (params.length <= 1) {
            return "OOPS!!! The description of a todo cannot be empty.";
        }
        Todos todo = new Todos(params[1]);
        int size = taskList.add(todo);
        return "Got it. I've added this task:\n" + todo + "\nNow you have "
                + size + " tasks in the list";
    }

    /**
     * Handles creating and adding a new Deadline task.
     *
     * @param input the user input
     * @param taskList the task list to modify
     * @return the chatbot response message
     */
    private String handleCreateDeadline(String input, TaskList taskList) {
        String[] params = input.split(" ", 2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        if (params.length <= 1 || !params[1].contains("/by ")) {
            return "OOPS!!! The description/due date of a deadline cannot be empty.";
        }
        String[] subParam = params[1].split("/by ");
        try {
            LocalDateTime dueDate = LocalDateTime.parse(subParam[1], formatter);
            if (dueDate.isBefore(LocalDateTime.now())) {
                return "Invalid input! Deadline is already past";
            }
            Deadlines deadline = new Deadlines(dueDate, subParam[0]);
            int size = taskList.add(deadline);
            return "Got it. I've added this task:\n" + deadline + "\nNow you have "
                    + size + " tasks in the list\n";
        } catch (DateTimeParseException e) {
            return "Invalid date/time provided: Please provide in the format dd/mm/yyyy HHmm";
        }
    }

    /**
     * Handles creating and adding a new Event task.
     *
     * @param input the user input
     * @param taskList the task list to modify
     * @return the chatbot response message
     */
    private String handleCreateEvent(String input, TaskList taskList) {
        String[] params = input.split(" ", 2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        if (params.length <= 1 || !params[1].contains("/from ")
                || !params[1].contains("/to ")) {
            return "OOPS!!! The description/start date/end date of an event cannot be empty.";
        }
        String[] split = params[1].split("/from ");
        String description = split[0];
        String[] subParam = split[1].split(" /to ");
        try {
            LocalDateTime start = LocalDateTime.parse(subParam[0], formatter);
            LocalDateTime end = LocalDateTime.parse(subParam[1], formatter);
            if (end.isBefore(LocalDateTime.now())) {
                return "Invalid input! Event is already over";
            }
            if (start.isAfter(end)) {
                return "Invalid input! Start date must be before end date";
            }
            Events event = new Events(description, start, end);
            int size = taskList.add(event);
            return "Got it. I've added this task:\n" + event + "\nNow you have "
                    + size + " tasks in the list";
        } catch (DateTimeParseException e) {
            return "Invalid date/time provided: Please provide in the format dd/mm/yyyy HHmm";
        }
    }

    /**
     * Handles deleting a task from the list.
     *
     * @param input the user input
     * @param taskList the task list to modify
     * @return the chatbot response message
     */
    private String handleDelete(String input, TaskList taskList) {
        String[] params = input.split(" ");
        if (params.length <= 1) {
            return "OOPS!!! The task index cannot be empty.";
        }
        try {
            int taskId = Integer.parseInt(params[1]);
            Task toRemove = taskList.getTask(taskId - 1);
            int size = taskList.remove(toRemove);
            return "Noted. I've removed this task: " + toRemove
                    + "\nNow you have " + size + " tasks in the list";
        } catch (NumberFormatException e) {
            return "OOPS!!! The entered task index is not a number.";
        } catch (NullPointerException | IndexOutOfBoundsException err) {
            return "OOPS!!! Task does not exist";
        }
    }

    /**
     * Handles finding tasks that match a search query.
     *
     * @param input the user input
     * @param taskList the task list to search
     * @return the chatbot response message
     */
    private String handleFind(String input, TaskList taskList) {
        String[] params = input.split(" ");
        if (params.length <= 1) {
            return "OOPS!!! Your search query cannot be empty.";
        }
        String result = taskList.filter(params[1]);
        return "Here are the matching tasks in your list:\n" + result;
    }
}
