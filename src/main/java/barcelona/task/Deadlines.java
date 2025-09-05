package barcelona.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline.
 * <p>
 * The {@code Deadline} class extends {@link Task} by adding a due date
 * to the task's description. This allows tasks to be tracked not only
 * by their description, but also by when they must be completed.
 * </p>
 */
public class Deadlines extends Task {
    private final LocalDateTime deadline;

    /**
     * Creates deadline object
     * @param deadline - due date
     * @param description - description for deadline
     */
    public Deadlines(LocalDateTime deadline, String description) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
        return "[D]" + super.toString() + " (by: " + this.deadline.format(outputFormatter) + ")";
    }

    @Override
    public String export() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return "D | " + (this.isDone ? 1 : 0) + " | " + this.description + " | "
                + this.deadline.format(formatter);
    }
}
