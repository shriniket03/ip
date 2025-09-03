package barcelona.ui;

public class Ui {
    private final String line = "____________________________________________________________";
    public void greet() {
        System.out.println(line + """
                \nHello! I'm Barcelona
                What can I do for you?\n"""
                + line);
    }
    public void exit() {
        System.out.println(line + """
                \nBye. Hope to see you again soon!
                """ + line);
    }
    public void log(String message) {
        System.out.println(line + "\n" + message + "\n" + line);
    }
}
