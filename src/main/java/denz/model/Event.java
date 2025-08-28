package denz.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an {@link Task} that occurs during a specific time period.
 * An event has a description, completion status, a start date/time, and an end date/time.
 */
public class Event extends Task {
    private String description;
    private boolean isDone;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    /**
     * Constructs a new Event task with a given description, start date/time, and end date/time.
     *
     * @param description Description of the event task.
     * @param startDate   The {@link LocalDateTime} when the event begins.
     * @param endDate     The {@link LocalDateTime} when the event ends.
     */
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns the start date/time of the event.
     *
     * @return The {@link LocalDateTime} representing when the event starts.
     */
    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    /**
     * Returns the end date/time of the event.
     *
     * @return The {@link LocalDateTime} representing when the event ends.
     */
    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    /**
     * Returns the string representation of the event task.
     * Format: [E] {super.toString()} (from: startDate to: endDate).
     *
     * @return A formatted string describing this event task.
     */
    @Override
    public String toString() {
        DateTimeFormatter F = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        return "[E] " + super.toString() + "(from: "
                + this.getStartDate().format(F)
                + " to: " + this.getEndDate().format(F) + ")";
    }
}