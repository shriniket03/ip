public class Deadlines extends Task{
    private String deadline;

    public Deadlines(String deadline, String description) {
        super(description);
        this.deadline = deadline;
    }

    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.deadline + ")";
    }
}
