public class Todos extends Task{

    public Todos(String description) {
        super(description);
    }

    public String toString() {
        return "[T]" + super.toString();
    }
}
