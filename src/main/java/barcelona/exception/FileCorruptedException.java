package barcelona.exception;

public class FileCorruptedException extends Exception {
    /**
     * Constructor for the custom FileCorruptedException
     * <p>This constructor is responsible for creating
     * the FileCorruptedException exception so that
     * it can be handled if user tries to import a
     * corrupted file into the tasklist </p>
     * @param message description of error
     */
    public FileCorruptedException(String message) {
        super(message);
    }
}
