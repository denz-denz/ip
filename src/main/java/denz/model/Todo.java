package denz.model;

/**
 * Represents a to-do task.
 * A {@code Todo} is a {@link Task} without a deadline or time period.
 */
public class Todo extends Task {
    private String Description;
    private boolean isDone;

    /**
     * Creates a {@code Todo} task with the given description.
     *
     * @param Description Description of the task.
     */
    public Todo(String Description) {
        super(Description);
    }

    /**
     * Returns a string representation of this {@code Todo}.
     * The format includes the type indicator {@code [T]} followed by
     * the base {@link Task} string.
     *
     * @return A string representation of this task.
     */
    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
