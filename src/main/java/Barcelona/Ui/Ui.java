package Barcelona.Ui;

public class Ui {
    String LINE = "____________________________________________________________";
    public void greet() {
        System.out.println(LINE + """
                \nHello! I'm Barcelona
                What can I do for you?\n"""
                + LINE);
    }
    public void exit() {
        System.out.println(LINE + """
                \nBye. Hope to see you again soon!
                """ + LINE);
    }
    public void log(String message) {
        System.out.println(LINE + "\n" + message + "\n" + LINE);
    }
}
