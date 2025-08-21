public class Todo extends Task{
    private String Description;
    private boolean isDone;
    public Todo(String Description) {
        super(Description);
    }
    @Override
    public String toString(){
        return "[T] " + super.toString();
    }
}
