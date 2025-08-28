package denz.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a {@link Task} with a deadline.
 * A deadline has a description, completion status, and a due date/time.
 */
public class Deadline extends Task {
    private String description;
    private boolean isDone;
    private final LocalDateTime dueDate;

    /**
     * Constructs a new Deadline task with a given description and due date.
     *
     * @param description Description of the deadline task.
     * @param dueDate     Date and time by which the task must be completed.
     */
    public Deadline(String description, LocalDateTime dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    /**
     * Returns the due date/time of the deadline task.
     *
     * @return The {@link LocalDateTime} of the deadline.
     */
    public LocalDateTime getDueDate() {
        return this.dueDate;
    }

    /**
     * Returns the string representation of the deadline task.
     * Format: [D] {super.toString()} (by: MMM d yyyy HH:mm).
     *
     * @return A formatted string describing this deadline task.
     */
    @Override
    public String toString() {
        DateTimeFormatter F = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        return "[D] " + super.toString() + "(by: " + this.getDueDate().format(F) + ")";
    }
}
