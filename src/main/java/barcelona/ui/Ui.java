package barcelona.ui;

/**
 * Implementation for non-GUI version of chatbot
 */
public class Ui {
    private final String line = "____________________________________________________________";

    /**
     * To greet user in the non-GUI format
     */
    public void greet() {
        System.out.println(line + """
                \nHello! I'm Barcelona
                What can I do for you?\n"""
                + line);
    }

    /**
     * To exit application in the non-GUI format
     */
    public void exit() {
        System.out.println(line + """
                \nBye. Hope to see you again soon!
                """ + line);
    }

    /**
     * To log errors in the non-GUI format
     * @param message - message to be logged
     */
    public void log(String message) {
        System.out.println(line + "\n" + message + "\n" + line);
    }
}
