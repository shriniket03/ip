public class Todos extends Task{

    public Todos(String description) {
        super(description);
    }

    public String toString() {
        return "[T]" + super.toString();
    }

    public String export() {
        return "T | " + (this.isDone ? 1 : 0) + " | " + this.description;
    }
}
