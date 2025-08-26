public class Events extends Task{
    private String start;
    private String end;

    public Events(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }

    public String export() {
        return "E | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + this.start + " | " + this.end;
    }
}
