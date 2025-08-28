package denz.parser;

import denz.app.Denz;
import denz.command.*;
import denz.exception.AddException;
import denz.exception.ByeException;
import denz.exception.DenzException;
import denz.exception.FindException;
import denz.exception.IndexException;
import denz.util.DateTimeUtil;
import java.time.LocalDateTime;

public class Parser {

    public static Command parse(String input) throws DenzException {
        String line = input.trim();
        if (line.isEmpty()) {
            return new NoOpCommand();
        }
        String[] parts = line.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String rest = (parts.length > 1) ? parts[1] : "";
        if (cmd.equals("list")) {
            return new ListCommand();
        }
        else if (cmd.equals("bye")) {
            return parseBye(line);
        }
        else if (cmd.equals("todo") || cmd.equals("deadline") || cmd.equals("event")) {
            return parseAdd(line);
        }
        else if (cmd.equals("mark")) {
            return parseMark(rest);
        }
        else if (cmd.equals("unmark")) {
            return parseUnmark(rest);
        }
        else if (cmd.equals("delete")) {
            return parseDelete(rest);
<<<<<<< Updated upstream
        }
        else {
=======
        } else if (cmd.equals("find")) {
            return parseFind(line);
        } else {
>>>>>>> Stashed changes
            throw new DenzException("I have no idea what you want: " + cmd);
        }
    }

    /** One-based index like "3" -> 3, else throws with your message. */
    public static int parseIndex(String token, String errorMessage) throws IndexException {
        try {
            return Integer.parseInt(token.trim());
        } catch (NumberFormatException e) {
            throw new IndexException(errorMessage);
        }
    }
    public static Command parseMark(String rest) throws IndexException {
        return new MarkCommand(parseIndex(rest, "invalid task index to mark"));
    }

    public static Command parseUnmark(String rest) throws IndexException {
        return new UnmarkCommand(parseIndex(rest, "invalid task index to unmark"));
    }

    public static Command parseDelete(String rest) throws IndexException {
        return new DeleteCommand(parseIndex(rest, "invalid task index to delete"));
    }

    /** Validate "bye" must have nothing after it. */
    public static Command parseBye(String line) throws ByeException {
        if (!line.startsWith("bye")) {
            throw new ByeException("invalid command to exit");
        }
        if (!line.substring(3).isEmpty()){
            throw new ByeException("just say bye, dont add anything after bye");
        }
        return new ByeCommand();
    }

    /** Build a denz.model.Task from a full add command (todo|deadline|event ...). */
    public static Command parseAdd(String fullLine) throws AddException {
        String line = fullLine.trim();
        String[] parts = line.split("\\s+", 2);
        String cmd = parts[0].toLowerCase();
        String rest = (parts.length > 1) ? parts[1] : "";
        switch (cmd) {
        case "todo": {
            String description = line.substring(4).trim();
            if (description.isEmpty()) {
                throw new AddException("You're trolling me right. You are missing the task description");
            }
            return new AddTodoCommand(description);
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
            return new AddDeadlineCommand(description, by);
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
                throw new AddException("denz.model.Event end must be after start.");
            }
            return new AddEventCommand(description, start, end);
        }

        default:
            throw new AddException("I do not have a clue what you want me to add");
        }
    }

    /**
     * Parses a {@code find} command from the user input.
     * <p>
     * The expected format is: {@code find <keyword>}.
     * If no keyword is provided, a {@link denz.exception.FindException} is thrown.
     *
     * @param fullLine the full user input string containing the find command
     * @return a {@link FindCommand} initialized with the search keyword
     * @throws DenzException if the user input does not include a keyword
     */
    public static Command parseFind(String fullLine) throws DenzException {
        String line = fullLine.trim();
        String[] parts = line.split("\\s+", 2);
        if (parts.length < 2) {
            throw new FindException("What do you want me to find???");
        }
        String rest = parts[1];
        return new FindCommand(rest);
    }
}

