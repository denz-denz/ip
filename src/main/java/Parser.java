import java.time.LocalDateTime;
public class Parser {
    public static String commandWord(String line) {
        String[] parts = line.trim().split("\\s+", 2);
        return parts.length == 0 ? "" : parts[0].toLowerCase();
    }

    /** Everything after the command word (may be empty). */
    public static String rest(String line) {
        String[] parts = line.trim().split("\\s+", 2);
        return (parts.length > 1) ? parts[1] : "";
    }

    /** One-based index like "3" -> 3, else throws with your message. */
    public static int parseIndex(String token, String errorMessage) throws IndexException {
        try {
            return Integer.parseInt(token.trim());
        } catch (NumberFormatException e) {
            throw new IndexException(errorMessage);
        }
    }
    public static int parseMark(String rest) throws IndexException {
        return parseIndex(rest, "invalid task index to mark");
    }

    public static int parseUnmark(String rest) throws IndexException {
        return parseIndex(rest, "invalid task index to unmark");
    }

    public static int parseDelete(String rest) throws IndexException {
        return parseIndex(rest, "invalid task index to delete");
    }

    /** Validate "bye" must have nothing after it. */
    public static void validateBye(String line) throws ByeException {
        if (!line.startsWith("bye")) {
            throw new ByeException("invalid command to exit");
        }
        if (!line.substring(3).isEmpty()){
            throw new ByeException("just say bye, dont add anything after bye");
        }
    }

    /** Build a Task from a full add command (todo|deadline|event ...). */
    public static Task parseAdd(String fullLine) throws AddException {
        String cmd = commandWord(fullLine);
        String line = fullLine.trim();

        switch (cmd) {
        case "todo": {
            String description = line.substring(4).trim();
            if (description.isEmpty()) {
                throw new AddException("You're trolling me right. You are missing the task description");
            }
            return new Todo(description);
        }

        case "deadline": {
            String body = line.substring(8).trim();
            String[] seg = body.split("\\s*/by\\s*", 2);
            if (seg.length < 2) {
                throw new AddException("Usage: deadline <description> /by <yyyy-MM-dd[ HHmm]>");
            }
            String description = seg[0].trim();
            if (description.isEmpty()) {
                throw new AddException("wthelly, you're trolling me right. You are missing the task description");
            }
            LocalDateTime by = DateTimeUtil.parse(seg[1].trim());
            return new Deadline(description, by);
        }

        case "event": {
            String body = line.substring(5).trim();
            String[] s1 = body.split("\\s*/from\\s*", 2);
            if (s1.length < 2) {
                throw new AddException("Usage: event <description> /from <start> /to <end>");
            }
            String description = s1[0].trim();
            if (description.isEmpty()) {
                throw new AddException("wthelly, you're trolling me right. You are missing the task description");
            }
            String[] s2 = s1[1].split("\\s*/to\\s*", 2);
            if (s2.length < 2) {
                throw new AddException("Usage: event <description> /from <start> /to <end>");
            }
            LocalDateTime start = DateTimeUtil.parse(s2[0].trim());
            LocalDateTime end   = DateTimeUtil.parse(s2[1].trim());
            if (!end.isAfter(start)) {
                throw new AddException("Event end must be after start.");
            }
            return new Event(description, start, end);
        }

        default:
            throw new AddException("I do not have a clue what you want me to add");
        }
    }
}

