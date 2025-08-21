public class Event extends Task{
    private String description;
    private boolean isDone;
    private String startDate;
    private String endDate;
    public Event(String description, String startDate, String endDate){
        super(description);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public String getStartDate(){
        return this.startDate;
    }
    public String getEndDate(){
        return this.endDate;
    }
    @Override
    public String toString(){
        return "[E] " + super.toString() + "(from: " + this.getStartDate() + " to: " + this.getEndDate() + ")";
    }
}
