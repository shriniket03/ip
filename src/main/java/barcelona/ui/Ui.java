package barcelona.ui;

/**
 * Implementation for non-GUI version of chatbot
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";

    /**
     * To greet user in the non-GUI format
     */
    public void greet() {
        System.out.println(LINE + """
                \nHello! I'm Barcelona
                What can I do for you?\n"""
                + LINE);
    }

    /**
     * To exit application in the non-GUI format
     */
    public void exit() {
        System.out.println(LINE + """
                \nBye. Hope to see you again soon!
                """ + LINE);
    }

    /**
     * To log errors in the non-GUI format
     * @param message - message to be logged
     */
    public void log(String message) {
        System.out.println(LINE + "\n" + message + "\n" + LINE);
    }
}
