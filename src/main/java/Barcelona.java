import java.util.*;

public class Barcelona {
    public static void main(String[] args) {
        String LINE = "____________________________________________________________";
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
        } else {
            System.out.println(LINE + "\n" + input + "\n" +LINE);
        }
    }
    System.out.println(exit);
    }
}
