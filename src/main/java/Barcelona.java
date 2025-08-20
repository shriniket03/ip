import java.util.*;

public class Barcelona {
    public static void main(String[] args) {
        String LINE = "____________________________________________________________";
        String[] arr = new String[100];
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
                for (int j=1; j<=elems; j++) {
                    list.append(j).append(". ").append(arr[j - 1]).append("\n");
                }
                System.out.println(LINE + "\n" + list.toString() + LINE);
            }
            else {
                arr[elems++] = input;
                System.out.println(LINE + "\n added: " + input + "\n" + LINE);
            }
        }
        System.out.println(exit);
    }
}
