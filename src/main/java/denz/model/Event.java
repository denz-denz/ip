package denz.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import denz.util.DateTimeUtil;
public class Event extends Task {
    private String description;
    private boolean isDone;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public LocalDateTime getStartDate() {
        return this.startDate;
    }
    public LocalDateTime getEndDate() {
        return this.endDate;
    }
    @Override
    public String toString() {
        DateTimeFormatter F = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        return "[E] " + super.toString() + "(from: " + this.getStartDate().format(F) + " to: " + this.getEndDate().format(F) + ")";
    }
}
