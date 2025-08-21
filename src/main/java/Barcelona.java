import java.util.ArrayList;
import java.util.Scanner;

public class Barcelona {
    public static void main(String[] args) {
        String LINE = "____________________________________________________________";
        ArrayList<Task> arr = new ArrayList<>();

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

        String greet = LINE + """
                \nHello! I'm Barcelona
                What can I do for you?\n"""
                + LINE;
        Scanner sc = new Scanner(System.in);

        String exit = LINE + """
                \nBye. Hope to see you again soon!
                """ + LINE;

        System.out.println(greet);
        outer: while (true) {
            String input = sc.nextLine();
            Command action;
            try {
                action = Command.valueOf(input.split(" ")[0].toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println(LINE + "\n OOPS!!! I'm sorry, but I don't know what that means :-( \n" + LINE);
                continue;
            }
            String[] params;
            switch (action) {
                case BYE:
                    break outer;
                case LIST:
                    StringBuilder list = new StringBuilder();
                    for (int j = 1; j <= arr.size(); j++) {
                        list.append(j).append(". ").append(arr.get(j - 1)).append("\n");
                    }
                    System.out.println(LINE + "\n" + list + LINE);
                    break;
                case MARK:
                    params = input.split(" ");
                    if (params.length > 1) {
                        try {
                            int taskId = Integer.parseInt(params[1]);
                            arr.get(taskId - 1).markAsDone();
                            System.out.println(LINE + "\n Nice! I've marked this task as done: \n" + arr.get(taskId - 1) + "\n" + LINE);
                        } catch (NumberFormatException e) {
                            System.out.println(LINE + "\n OOPS!!! The entered task index is not a number. \n" + LINE);
                        } catch (NullPointerException | IndexOutOfBoundsException err) {
                            System.out.println(LINE + "\n OOPS!!! Task does not exist \n" + LINE);
                        }
                    } else {
                        System.out.println(LINE + "\n OOPS!!! The task index cannot be empty. \n" + LINE);
                    }
                    break;
                case UNMARK:
                    params = input.split(" ");
                    if (params.length > 1) {
                        try {
                            int taskId = Integer.parseInt(params[1]);
                            arr.get(taskId - 1).markAsUndone();
                            System.out.println(LINE + "\n OK, I've marked this task as not done yet: \n" + arr.get(taskId - 1)
                                    + "\n" + LINE);
                        } catch (NumberFormatException e) {
                            System.out.println(LINE + "\n OOPS!!! The entered task index is not a number. \n" + LINE);
                        } catch (NullPointerException | IndexOutOfBoundsException err) {
                            System.out.println(LINE + "\n OOPS!!! Task does not exist \n" + LINE);
                        }
                    } else {
                        System.out.println(LINE + "\n OOPS!!! The task index cannot be empty. \n" + LINE);
                    }
                    break;
                case TODO:
                    params = input.split(" ");
                    if (params.length > 1) {
                        Todos todo = new Todos(params[1]);
                        arr.add(todo);
                        System.out.println(LINE + "\n Got it. I've added this task:\n" + todo + "\n" +
                                "Now you have " + arr.size() + " tasks in the list\n" + LINE);
                    } else {
                        System.out.println(LINE + "\n OOPS!!! The description of a todo cannot be empty. \n" + LINE);
                    }
                    break;
                case DEADLINE:
                    params = input.split(" ", 2);
                    if (params.length > 1 && params[1].contains("/by ")) {
                        String[] subParam = params[1].split("/by ");
                        String dueDate = subParam[1];
                        Deadlines deadline = new Deadlines(dueDate, subParam[0]);
                        arr.add(deadline);
                        System.out.println(LINE + "\n Got it. I've added this task:\n" + deadline + "\n" +
                                "Now you have " + arr.size() + " tasks in the list\n" + LINE);
                    } else {
                        System.out.println(LINE + "\n OOPS!!! The description/due date of a deadline cannot be empty. \n" + LINE);
                    }
                    break;
                case EVENT:
                    params = input.split(" ", 2);
                    if (params.length > 1 && params[1].contains("/from ") && params[1].contains("/to ")) {
                        String[] split = params[1].split("/from ");
                        String description = split[0];
                        String[] subParam = split[1].split("/to ");
                        String start = subParam[0];
                        String end = subParam[1];

                        Events event = new Events(description, start, end);
                        arr.add(event);
                        System.out.println(LINE + "\n Got it. I've added this task:\n" + event + "\n" +
                                "Now you have " + arr.size() + " tasks in the list\n" + LINE);
                    } else {
                        System.out.println(LINE + "\n OOPS!!! The description/start date/end date of an event cannot be empty. \n" + LINE);
                    }
                    break;
                case DELETE:
                    params = input.split(" ");
                    if (params.length > 1) {
                        try {
                            int taskId = Integer.parseInt(params[1]);
                            Task toRemove = arr.get(taskId - 1);
                            arr.remove(toRemove);
                            System.out.println(LINE + "\n Noted. I've removed this task: \n" + toRemove
                                    + "\n" + "Now you have " + arr.size() + " tasks in the list\n" + LINE);
                        } catch (NumberFormatException e) {
                            System.out.println(LINE + "\n OOPS!!! The entered task index is not a number. \n" + LINE);
                        } catch (NullPointerException | IndexOutOfBoundsException err) {
                            System.out.println(LINE + "\n OOPS!!! Task does not exist \n" + LINE);
                        }
                    } else {
                        System.out.println(LINE + "\n OOPS!!! The task index cannot be empty. \n" + LINE);
                    }
                    break;
            }
        }
        System.out.println(exit);
    }
}

