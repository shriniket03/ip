package barcelona.task;

public class Todos extends Task {

    public Todos(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String export() {
        return "T | " + (this.isDone ? 1 : 0) + " | " + this.description;
    }
}
