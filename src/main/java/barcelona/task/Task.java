package barcelona.task;

/**
 * Task is a parent class for Todos, Events, Deadlines
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Parent class constructor
     * @param description - description of task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Converts each Task object to a storable format to be written to txt file
     * @return String representation of exported task
     */
    public abstract String export();
}
