package barcelona.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Events is a type of task that has a from date/time to end date/time
 */
public class Events extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;

    /**
     * Creates the event object
     * @param description - description of event
     * @param start - start date/time for event
     * @param end - end date/time for event
     */
    public Events(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy, h:mma");
        return "[E]" + super.toString() + " (from: " + this.start.format(outputFormatter)
                + " to: " + this.end.format(outputFormatter) + ")";
    }

    @Override
    public String export() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return "E | " + (this.isDone ? 1 : 0) + " | " + this.description + " | "
                + this.start.format(formatter) + " | " + this.end.format(formatter);
    }
}
