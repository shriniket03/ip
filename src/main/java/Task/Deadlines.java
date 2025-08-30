package Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadlines extends Task {
    private LocalDateTime deadline;

    public Deadlines(LocalDateTime deadline, String description) {
        super(description);
        this.deadline = deadline;
    }

    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
        return "[D]" + super.toString() + " (by: " + this.deadline.format(outputFormatter) + ")";
    }

    public String export() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return "D | " + (this.isDone ? 1 : 0) + " | " + this.description + " | "
                + this.deadline.format(formatter);
    }
}
