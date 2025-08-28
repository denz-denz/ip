package denz.storage;

import denz.model.Todo;
import denz.model.Task;
import denz.model.Deadline;
import denz.model.Event;
import denz.exception.CorruptLineException;
import denz.exception.AddException;
import denz.util.DateTimeUtil;

/**
 * Utility class for converting {@link Task} objects to and from their save-file string format.
 * <p>
 * The format is a pipe-separated string:
 * <ul>
 *   <li>Todo: {@code T|0|description}</li>
 *   <li>Deadline: {@code D|1|description|dueDate}</li>
 *   <li>Event: {@code E|0|description|startDate|endDate}</li>
 * </ul>
 * where {@code 1} indicates the task is marked as done, and {@code 0} indicates it is not done.
 */
public class TaskIO {
    /** Private constructor to prevent instantiation of utility class. */
    private TaskIO() { }

    /**
     * Converts a {@link Task} into a save line string.
     *
     * @param t the {@code Task} to convert
     * @return the string representation suitable for saving to file
     * @throws IllegalArgumentException if the task type is unknown
     */
    public static String toSaveLine(Task t) {
        if (t instanceof Todo) {
            return "T|" + (t.isDone() ? "1" : "0") + "|" + t.getDescription();
        } else if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D|" + (d.isDone() ? "1" : "0") + "|" + d.getDescription() + "|" + d.getDueDate();
        } else if (t instanceof Event) {
            Event e = (Event) t;
            return "E|" + (e.isDone() ? "1" : "0") + "|" + e.getDescription()
                    + "|" + e.getStartDate() + "|" + e.getEndDate();
        } else {
            throw new IllegalArgumentException("Unknown task type");
        }
    }

    /**
     * Converts a save line string back into a {@link Task}.
     *
     * @param line the save line string to parse
     * @return the {@code Task} object represented by the line
     * @throws CorruptLineException if the line is malformed, contains too few fields, has an empty description,
     *                              or the type is unknown
     */
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
        try {
            switch (type) {
            case "T": {
                Todo t = new Todo(desc);
                if (done) {
                    t.mark();
                }
                return t;
            }
            case "D": {
                if (parts.length < 4) {
                    throw new CorruptLineException("Bad deadline line: " + line);
                }
                String dueDateString = parts[3].trim();
                Deadline d = new Deadline(desc, DateTimeUtil.parse(dueDateString));
                if (done) {
                    d.mark();
                }
                return d;
            }
            case "E": {
                if (parts.length < 5) {
                    throw new CorruptLineException("Bad event line: " + line);
                }
                Event e = new Event(
                        desc,
                        DateTimeUtil.parse(parts[3].trim()),
                        DateTimeUtil.parse(parts[4].trim())
                );
                if (done) {
                    e.mark();
                }
                return e;
            }
            default:
                throw new CorruptLineException("Unknown task type: " + type);
            }
        } catch (AddException e) {
            System.out.println("Error!! " + e.getMessage());
        }
        throw new CorruptLineException("Unknown task type: " + type);
    }
}
