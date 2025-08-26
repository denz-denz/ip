package denz.storage;

import denz.model.Deadline;
import denz.model.Event;
import denz.model.Task;
import denz.model.Todo;
import denz.exception.CorruptLineException;
import denz.exception.AddException;
import denz.util.DateTimeUtil;

public class TaskIO {
    private TaskIO() {} // prevent instantiation

    /** Convert a denz.model.Task to a save line */
    public static String toSaveLine(Task t) {
        if (t instanceof Todo) {
            return "T|" + (t.isDone() ? "1" : "0") + "|" + t.getDescription();
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D|" + (d.isDone() ? "1" : "0") + "|" + d.getDescription() + "|" + d.getDueDate();
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return "E|" + (e.isDone() ? "1" : "0") + "|" + e.getDescription() + "|" + e.getStartDate() + "|" + e.getEndDate();
        } else {
            throw new IllegalArgumentException("Unknown task type");
        }
    }

    /** Convert a save line back into a denz.model.Task */
    public static Task fromSaveLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            throw new CorruptLineException("Too few fields!!");
        }
        String type = parts[0].trim();
        boolean done = parts[1].trim().equals("1");
        String desc = parts[2].trim();
        if (desc.isEmpty()) {
            throw new CorruptLineException("Empty task description!");
        }
        try{
            switch (type) {
                case "T": {
                    Todo t = new Todo(desc);
                    if (done) t.mark();
                    return t;
                }
                case "D": {
                    if (parts.length < 4) throw new CorruptLineException("Bad deadline line: " + line);
                    String dueDateString = parts[3].trim();
                    Deadline d = new Deadline(desc, DateTimeUtil.parse(dueDateString));
                    if (done) d.mark();
                    return d;
                }
                case "E": {
                    if (parts.length < 5) throw new CorruptLineException("Bad event line: " + line);
                    Event e = new Event(desc, DateTimeUtil.parse(parts[3].trim()), DateTimeUtil.parse(parts[4].trim()));
                    if (done) e.mark();
                    return e;
                }
                default:
                    throw new CorruptLineException("Unknown task type: " + type);
            }
        } catch (AddException e) {
            System.out.println("Error!! " + e.getMessage());
        }
        throw new CorruptLineException("Unknow task type: " + type);
    }
}
