package denz.model;

/**
 * Represents a generic task with a description and completion status.
 * Serves as the base class for more specific task types such as
 * {@link Todo}, {@link Deadline}, and {@link Event}.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a Task with the given description.
     * A newly created task is not marked as done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns whether this task is marked as done.
     *
     * @return {@code true} if the task is completed, {@code false} otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Unmarks this task, setting it as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Returns the string representation of this task,
     * showing whether it is done along with its description.
     * Format: "[X] description" if done, "[ ] description" otherwise.
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        return isDone ? "[X] " + this.getDescription() : "[ ] " + this.getDescription();
    }
}
