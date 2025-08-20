import java.util.Scanner;

public class Barcelona {
    public static void main(String[] args) {
        String LINE = "____________________________________________________________";
        Task[] arr = new Task[100];
        int elems = 0;

        String greet = LINE + """
                \nHello! I'm Barcelona
                What can I do for you?\n"""
                + LINE;
        Scanner sc = new Scanner(System.in);

        String exit = LINE + """
                \nBye. Hope to see you again soon!
                """ + LINE;

        System.out.println(greet);
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                StringBuilder list = new StringBuilder();
                for (int j = 1; j <= elems; j++) {
                    list.append(j).append(". ").append(arr[j - 1]).append("\n");
                }
                System.out.println(LINE + "\n" + list.toString() + LINE);
            } else if (input.split(" ")[0].equals("mark")) {
                String[] params = input.split(" ");
                if (params.length > 1) {
                    try {
                        int taskId = Integer.parseInt(params[1]);
                        arr[taskId - 1].markAsDone();
                        System.out.println(LINE + "\n Nice! I've marked this task as done: \n" + arr[taskId - 1] + "\n" + LINE);
                    } catch (NumberFormatException e) {
                        System.out.println(LINE + "\n bad input \n" + LINE);
                    } catch (NullPointerException | ArrayIndexOutOfBoundsException err) {
                        System.out.println(LINE + "\n task not found \n" + LINE);
                    }
                } else {
                    System.out.println(LINE + "\n bad input \n" + LINE);
                }
            } else if (input.split(" ")[0].equals("unmark")) {
                String[] params = input.split(" ");
                if (params.length > 1) {
                    try {
                        int taskId = Integer.parseInt(params[1]);
                        arr[taskId - 1].markAsUndone();
                        System.out.println(LINE + "\n OK, I've marked this task as not done yet: \n" + arr[taskId - 1]
                                + "\n" + LINE);
                    } catch (NumberFormatException e) {
                        System.out.println(LINE + "\n bad input \n" + LINE);
                    } catch (NullPointerException | ArrayIndexOutOfBoundsException err) {
                        System.out.println(LINE + "\n task not found \n" + LINE);
                    }
                } else {
                    System.out.println(LINE + "\n bad input \n" + LINE);
                }
            } else {
                arr[elems++] = new Task(input);
                System.out.println(LINE + "\n added: " + input + "\n" + LINE);
            }
        }
        System.out.println(exit);
    }
}

