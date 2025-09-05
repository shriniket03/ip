package barcelona.task;

/**
 * Represents a simple to-do task.
 * <p>
 * The {@code Todos} class extends {@link Task} without introducing
 * additional properties. It is used for tasks that only require
 * a description, without deadlines or event dates.
 * </p>
 */
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
