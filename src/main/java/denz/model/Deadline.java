package denz.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import denz.util.DateTimeUtil;
public class Deadline extends Task {
    private String description;
    private boolean isDone;
    private final LocalDateTime dueDate;
    public Deadline(String description, LocalDateTime dueDate){
        super(description);
        this.dueDate = dueDate;
    }
    public LocalDateTime getDueDate(){
        return this.dueDate;
    }
    @Override
    public String toString(){
        DateTimeFormatter F = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
        return "[D] " + super.toString() + "(by: " + this.getDueDate().format(F) + ")";
    }

}
