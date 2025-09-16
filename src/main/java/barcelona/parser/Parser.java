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
 * Creates a parser & listener for user input
 */
public class Parser {
    /**
     * Types of allowable commands
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
        FIND
    }

    /**
     * Gives chatbot reply to user input
     * @param input - user input as String
     * @param taskList - stored tasklist
     * @param storage - storage instance to fetch txt file
     * @return - chatbot response
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
        String[] params;
        switch (action) {
        case BYE:
            response = "Bye. Hope to see you again soon!";
            break;
        case LIST:
            response = taskList.list();
            break;
        case MARK: {
            response = handleMark(input, taskList);
            break;
        }
        case UNMARK: {
            response = handleUnmark(input, taskList);
            break;
        }
        case TODO: {
            response = handleCreateTodo(input, taskList);
            break;
        }
        case DEADLINE: {
            response = handleCreateDeadline(input, taskList);
            break;
        }
        case EVENT: {
            response = handleCreateEvent(input, taskList);
            break;
        }
        case DELETE: {
            response = handleDelete(input, taskList);
            break;
        }
        case FIND: {
            response = handleFind(input, taskList);
            break;
        }
        default:
            response = "OOPS!!! I'm sorry, but I don't know what that means :-(";
            break;
        }
        storage.write(taskList.getList());
        return response;
    }

    /**
     * Logic for handling mark action
     * @param input - input string
     * @param taskList - TaskList object to manage tasks
     * @return reply to user in GUI
     */
    private String handleMark(String input, TaskList taskList) {
        String[] params = input.split(" ");
        String response;
        boolean isValidParam = params.length > 1;
        if (!isValidParam) {
            response = "OOPS!!! The task index cannot be empty.";
            return response;
        }
        try {
            int taskId = Integer.parseInt(params[1]);
            taskList.markDone(taskId - 1);
            response = "Nice! I've marked this task as done: \n"
                    + taskList.getTask(taskId - 1);
        } catch (NumberFormatException e) {
            response = "OOPS!!! The entered task index is not a number.";
        } catch (NullPointerException | IndexOutOfBoundsException err) {
            response = "OOPS!!! Task does not exist";
        }
        return response;
    }

    /**
     * Logic to handle unmark action
     * @param input - input string from user
     * @param taskList - TaskList object to manage tasks
     * @return reply to user in GUI
     */
    private String handleUnmark(String input, TaskList taskList) {
        String[] params = input.split(" ");
        String response;
        boolean isValidParam = params.length > 1;
        if (!isValidParam) {
            response = "OOPS!!! The task index cannot be empty.";
            return response;
        }
        try {
            int taskId = Integer.parseInt(params[1]);
            taskList.markUndone(taskId - 1);
            response = "OK, I've marked this task as not done yet: \n"
                    + taskList.getTask(taskId - 1);
        } catch (NumberFormatException e) {
            response = "OOPS!!! The entered task index is not a number.";
        } catch (NullPointerException | IndexOutOfBoundsException err) {
            response = "OOPS!!! Task does not exist";
        }
        return response;
    }

    /**
     * Logic to handle creation of a todo item
     * @param input - Input String from user
     * @param taskList - TaskList Object to manage tasks
     * @return GUI reply to user
     */
    private String handleCreateTodo(String input, TaskList taskList) {
        String[] params = input.split(" ");
        String response;
        boolean isValidParam = params.length > 1;
        if (!isValidParam) {
            response = "OOPS!!! The description of a todo cannot be empty.";
            return response;
        }
        Todos todo = new Todos(params[1]);
        int size = taskList.add(todo);
        response = "Got it. I've added this task:\n" + todo + "\n" + "Now you have "
                + size + " tasks in the list";
        return response;
    }

    /**
     * Logic to handle creation of new Deadline
     * @param input - Input String from User
     * @param taskList - TaskList Object to manage tasks
     * @return - GUI reply to user
     */
    private String handleCreateDeadline(String input, TaskList taskList) {
        String[] params = input.split(" ", 2);
        String response;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        boolean isValidParam = params.length > 1 && params[1].contains("/by ");
        if (!isValidParam) {
            response = "OOPS!!! The description/due date of a deadline cannot be empty.";
            return response;
        }
        String[] subParam = params[1].split("/by ");
        try {
            LocalDateTime dueDate = LocalDateTime.parse(subParam[1], formatter);
            Deadlines deadline = new Deadlines(dueDate, subParam[0]);
            int size = taskList.add(deadline);
            response = "Got it. I've added this task:\n" + deadline + "\n"
                    + "Now you have " + size + " tasks in the list\n";
        } catch (DateTimeParseException e) {
            response = "Invalid date/time provided";
        }
        return response;
    }

    /**
     * Logic to handle creation of Event
     * @param input - Input String from User
     * @param taskList - TaskList Object to manage tasks
     * @return - GUI reply to user
     */
    private String handleCreateEvent(String input, TaskList taskList) {
        String[] params = input.split(" ", 2);
        String response;
        // default date time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        boolean isValidParam = params.length > 1 && params[1].contains("/from ")
                && params[1].contains("/to ");
        if (!isValidParam) {
            response = "OOPS!!! The description/start date/end date of an event cannot be empty.";
            return response;
        }
        String[] split = params[1].split("/from ");
        String description = split[0];
        String[] subParam = split[1].split(" /to ");
        try {
            LocalDateTime start = LocalDateTime.parse(subParam[0], formatter);
            LocalDateTime end = LocalDateTime.parse(subParam[1], formatter);

            Events event = new Events(description, start, end);
            int size = taskList.add(event);
            response = "Got it. I've added this task:\n" + event + "\n"
                    + "Now you have " + size + " tasks in the list";
        } catch (DateTimeParseException e) {
            response = "Invalid date/time provided";
        }
        return response;
    }

    /**
     * Logic to handle deletion of a task
     * @param input - Input String from user
     * @param taskList - TaskList Object to Manage Tasks
     * @return - GUI Reply to User
     */
    private String handleDelete(String input, TaskList taskList) {
        String[] params = input.split(" ");
        String response;
        boolean isValidParam = params.length > 1;
        if (!isValidParam) {
            response = "OOPS!!! The task index cannot be empty.";
            return response;
        }
        try {
            int taskId = Integer.parseInt(params[1]);
            Task toRemove = taskList.getTask(taskId - 1);
            int size = taskList.remove(toRemove);
            response = "Noted. I've removed this task: " + toRemove
                    + "\n" + "Now you have " + size + " tasks in the list";
        } catch (NumberFormatException e) {
            response = "OOPS!!! The entered task index is not a number.";
        } catch (NullPointerException | IndexOutOfBoundsException err) {
            response = "OOPS!!! Task does not exist";
        }
        return response;
    }

    /**
     * Logic to handle find task action
     * @param input - Input String from user
     * @param taskList - TaskList Object to Manage Tasks
     * @return - GUI Reply to User
     */
    private String handleFind(String input, TaskList taskList) {
        String[] params = input.split(" ");
        String response;
        boolean isValidParam = params.length > 1;
        if (!isValidParam) {
            response = "OOPS!!! Your search query cannot be empty.";
            return response;
        }
        String result = taskList.filter(params[1]);
        response = "Here are the matching tasks in your list:\n" + result;
        return response;
    }
}
