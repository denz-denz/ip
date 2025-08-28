package denz.parser;

import denz.command.*;
import denz.exception.AddException;
import denz.exception.ByeException;
import denz.exception.DenzException;
import denz.exception.IndexException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    /* -------------------- BASIC / NO-OP -------------------- */

    @Test
    void parse_emptyLine_returnsNoOp() throws DenzException {
        Command c = Parser.parse("   ");
        assertTrue(c instanceof NoOpCommand);
    }

    @Test
    void parse_list_returnsListCommand() throws DenzException {
        Command c = Parser.parse("list");
        assertTrue(c instanceof ListCommand);
    }

    /* -------------------- BYE -------------------- */

    @Test
    void parse_bye_returnsByeCommand() throws DenzException {
        Command c = Parser.parse("bye");
        assertTrue(c instanceof ByeCommand);
    }

    @Test
    void parse_bye_withTrailingText_throws() {
        assertThrows(ByeException.class, () -> Parser.parse("bye now"));
    }

    /* -------------------- ADD (todo/deadline/event) -------------------- */

    @Test
    void parse_todo_returnsAddTodoCommand() throws DenzException {
        Command c = Parser.parse("todo read book");
        assertTrue(c instanceof AddTodoCommand);
    }

    @Test
    void parse_deadline_returnsAddDeadlineCommand() throws DenzException {
        Command c = Parser.parse("deadline return book /by 2019-12-10 1400");
        assertTrue(c instanceof AddDeadlineCommand);
    }

    @Test
    void parse_event_returnsAddEventCommand() throws DenzException {
        Command c = Parser.parse("event project meeting /from 2019-12-10 1400 /to 2019-12-10 1600");
        assertTrue(c instanceof AddEventCommand);
    }

    @Test
    void parse_deadline_missingBy_throwsAddException() {
        assertThrows(AddException.class, () -> Parser.parse("deadline return book"));
        assertThrows(AddException.class, () -> Parser.parse("deadline return /by")); // empty date
    }

    @Test
    void parse_event_missingFromOrTo_throwsAddException() {
        assertThrows(AddException.class, () -> Parser.parse("event meeting"));
        assertThrows(AddException.class, () -> Parser.parse("event meeting /from 2019-12-10 1400"));
        assertThrows(AddException.class, () -> Parser.parse("event meeting /to 2019-12-10 1600"));
    }

    /* -------------------- INDEXED COMMANDS (mark/unmark/delete) -------------------- */

    @Test
    void parse_mark_returnsMarkCommand() throws DenzException {
        Command c = Parser.parse("mark 2");
        assertTrue(c instanceof MarkCommand);
    }

    @Test
    void parse_unmark_returnsUnmarkCommand() throws DenzException {
        Command c = Parser.parse("unmark 1");
        assertTrue(c instanceof UnmarkCommand);
    }

    @Test
    void parse_delete_returnsDeleteCommand() throws DenzException {
        Command c = Parser.parse("delete 3");
        assertTrue(c instanceof DeleteCommand);
    }

    @Test
    void parse_mark_withNonInteger_throwsIndexException() {
        assertThrows(IndexException.class, () -> Parser.parse("mark two"));
    }

    @Test
    void parse_unmark_withNonInteger_throwsIndexException() {
        assertThrows(IndexException.class, () -> Parser.parse("unmark x"));
    }

    @Test
    void parse_delete_withNonInteger_throwsIndexException() {
        assertThrows(IndexException.class, () -> Parser.parse("delete abc"));
    }

    /* -------------------- UNKNOWN -------------------- */
    @Test
    void parse_unknown_throwsDenzException() {
        assertThrows(DenzException.class, () -> Parser.parse("wut"));
    }
}

