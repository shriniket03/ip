package barcelona.ui;

/**
 * Implementation for non-GUI version of chatbot
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    /**
     * To log errors in the non-GUI format
     * @param message - message to be logged
     */
    public void log(String message) {
        System.out.println(LINE + "\n" + message + "\n" + LINE);
    }
}
