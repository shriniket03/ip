package Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Events extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    public Events(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
        return "[E]" + super.toString() + " (from: " + this.start.format(outputFormatter)
                + " to: " + this.end.format(outputFormatter) + ")";
    }

    public String export() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return "E | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " +
                this.start.format(formatter) + " | " + this.end.format(formatter);
    }
}
