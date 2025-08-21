public class Deadline extends Task{
    private String description;
    private boolean isDone;
    private String dueDate;
    public Deadline(String description, String dueDate){
        super(description);
        this.dueDate = dueDate;
    }
    public String getDueDate(){
        return this.dueDate;
    }
    @Override
    public String toString(){
        return "[D] " + super.toString() + "(by: " + this.getDueDate() + ")";
    }

}
