public class Task {
    private String description;
    private boolean isDone;
    public Task(String description){
        this.description = description;
        this.isDone = false;
    }
    public String getDescription(){
        return this.description;
    }
    public boolean isDone(){
        return this.isDone;
    }
    public void mark(){
        this.isDone = true;
    }
    public void unmark(){
        this.isDone = false;
    }
    @Override
    public String toString(){
        return isDone ? "[X] " + this.getDescription() : "[ ] " + this.getDescription();
    }
}
