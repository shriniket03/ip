public class Deadlines extends Task{
    private String deadline;

    public Deadlines(String deadline, String description) {
        super(description);
        this.deadline = deadline;
    }

    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }

    public String export() {
        return "D | " + (this.isDone ? 1 : 0) + " | " + this.description + " | " + this.deadline;
    }
}
