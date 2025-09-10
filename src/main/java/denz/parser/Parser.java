package denz.parser;

import java.time.LocalDateTime;
import java.util.Arrays;

import denz.command.AddDeadlineCommand;
import denz.command.AddEventCommand;
import denz.command.AddTodoCommand;
import denz.command.ByeCommand;
import denz.command.Command;
import denz.command.DeleteCommand;
import denz.command.FindCommand;
import denz.command.ListCommand;
import denz.command.MarkCommand;
import denz.command.NoOpCommand;
import denz.command.RemindCommand;
import denz.command.UnmarkCommand;
import denz.exception.AddException;
import denz.exception.ByeException;
import denz.exception.DenzException;
import denz.exception.FindException;
import denz.exception.IndexException;
import denz.exception.RemindException;
import denz.util.DateTimeUtil;



/**
 * Parses raw user input into {@link Command} objects for execution.
 */
public class Parser {

    /**
     * Parses a full user input string and returns the corresponding {@link Command}.
     *
     * @param input raw user input string
     * @return a {@link Command} representing the parsed user input
     * @throws DenzException if the command is invalid or cannot be parsed
     */
    public static Command parse(String input) throws DenzException {
        String line = input.trim();
        if (line.isEmpty()) {
            return new NoOpCommand();
        }
        String[] parts = line.split("\\s+", 2);
        assert parts.length > 0 : "Parser failed to split empty input";
        String cmd = parts[0].toLowerCase();
        String rest = (parts.length > 1) ? parts[1] : "";

        if (cmd.equals("list")) {
            return new ListCommand();
        } else if (cmd.equals("bye")) {
            return parseBye(line);
        } else if (cmd.equals("todo") || cmd.equals("deadline") || cmd.equals("event")) {
            return parseAdd(line);
        } else if (cmd.equals("mark")) {
            return parseMark(rest);
        } else if (cmd.equals("unmark")) {
            return parseUnmark(rest);
        } else if (cmd.equals("delete")) {
            return parseDelete(rest);
        } else if (cmd.equals("find")) {
            return parseFind(line);
        } else if (cmd.equals("remind")) {
            return parseRemind(line);
        } else {
            throw new DenzException("I have no idea what you want: " + cmd);
        }
    }

    /**
     * Parses the raw user input for the {@code remind} command.
     * <p>
     * Expected formats:
     * <ul>
     *     <li>{@code remind} – creates a {@link RemindCommand} with the default limit of 10.</li>
     *     <li>{@code remind <limit>} – creates a {@link RemindCommand} with the specified integer limit.</li>
     * </ul>
     * If the input contains more than one argument after {@code remind},
     * or if the limit is not a valid integer, a {@link RemindException} is thrown.
     *
     * @param fullLine the full user input line starting with the {@code remind} keyword
     * @return a {@link RemindCommand} constructed from the parsed input
     * @throws RemindException if the input is invalid (too many arguments or non-integer limit)
     */

    public static Command parseRemind(String fullLine) throws RemindException {
        String line = fullLine.trim();
        String[] parts = line.split("\\s+");
        if (parts.length > 2) {
            throw new RemindException("Please only give me one chosen limit to have to remind you about the task!!");
        }
        if (parts.length == 1) {
            return new RemindCommand();
        }
        try {
            int limit = Integer.valueOf(parts[1]);
            return new RemindCommand(limit);
        } catch (NumberFormatException e) {
            throw new RemindException("Use an integer as a limit!");
        }
    }

    /**
     * Parses a one-based index string into an integer.
     *
     * @param token        the string token containing the index
     * @param errorMessage the error message to throw if parsing fails
     * @return the parsed integer index
     * @throws IndexException if the token is not a valid integer
     */
    public static int parseIndex(String token, String errorMessage) throws IndexException {
        try {
            return Integer.parseInt(token.trim());
        } catch (NumberFormatException e) {
            throw new IndexException(errorMessage);
        }
    }

    /**
     * Parses a mark command.
     *
     * @param rest the arguments after "mark"
     * @return a {@link MarkCommand}
     * @throws IndexException if the index is invalid
     */
    public static Command parseMark(String rest) throws IndexException {
        return new MarkCommand(parseIndex(rest, "invalid task index to mark"));
    }

    /**
     * Parses an unmark command.
     *
     * @param rest the arguments after "unmark"
     * @return an {@link UnmarkCommand}
     * @throws IndexException if the index is invalid
     */
    public static Command parseUnmark(String rest) throws IndexException {
        return new UnmarkCommand(parseIndex(rest, "invalid task index to unmark"));
    }

    /**
     * Parses a delete command.
     *
     * @param rest the arguments after "delete"
     * @return a {@link DeleteCommand}
     * @throws IndexException if the index is invalid
     */
    public static Command parseDelete(String rest) throws IndexException {
        return new DeleteCommand(parseIndex(rest, "invalid task index to delete"));
    }

    /**
     * Parses a bye command.
     *
     * @param line the full input line
     * @return a {@link ByeCommand}
     * @throws ByeException if extra text is present after "bye"
     */
    public static Command parseBye(String line) throws ByeException {
        if (!line.startsWith("bye")) {
            throw new ByeException("invalid command to exit");
        }
        if (!line.substring(3).isEmpty()) {
            throw new ByeException("just say bye, dont add anything after bye");
        }
        return new ByeCommand();
    }

    /**
     * Parses a task-adding command (todo, deadline, or event).
     *
     * @param fullLine the full input line
     * @return the corresponding {@link Command}
     * @throws AddException if the input format is invalid
     */
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
                throw new AddException("Invalid fields!");
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
                throw new AddException("Invalid fields!!");
            }
            String description = s1[0].trim();
            if (description.isEmpty()) {
                throw new AddException("wthelly, you're trolling me right. You are missing the task description");
            }
            String[] s2 = s1[1].split("\\s*/to\\s*", 2);
            if (s2.length < 2) {
                throw new AddException("Invalid dates!!");
            }
            LocalDateTime start = DateTimeUtil.parse(s2[0].trim());
            LocalDateTime end = DateTimeUtil.parse(s2[1].trim());
            if (!end.isAfter(start)) {
                throw new AddException("End date for event must be after start date.");
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
     * Expected format: {@code find <keyword>}.
     *
     * @param fullLine the full user input string containing the find command
     * @return a {@link FindCommand} initialized with the search keyword
     * @throws DenzException if the user input does not include a keyword
     */
    public static Command parseFind(String fullLine) throws DenzException {
        String line = fullLine.trim();
        String[] parts = line.split("\\s+");
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new FindException("What do you want me to find???");
        }
        String[] keywords = Arrays.copyOfRange(parts, 1, parts.length);
        return new FindCommand(keywords);
    }
}

