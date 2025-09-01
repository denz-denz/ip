package denz.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import denz.exception.AddException;

/**
 * Utility class for parsing date and time strings into {@link LocalDateTime}.
 * <p>
 * Supports multiple date and date-time formats such as:
 * <ul>
 *     <li>yyyy-MM-dd HHmm</li>
 *     <li>yyyy/M/d HHmm</li>
 *     <li>d/M/yyyy HHmm</li>
 *     <li>d-M-yyyy HHmm</li>
 *     <li>d MMM yyyy HHmm</li>
 *     <li>MMM d yyyy HHmm</li>
 *     <li>yyyy-MM-dd</li>
 *     <li>d/M/yyyy</li>
 *     <li>d-M-yyyy</li>
 *     <li>d MMM yyyy</li>
 *     <li>MMM d yyyy</li>
 * </ul>
 * <p>
 * Also accepts strict ISO {@code LocalDateTime} strings (e.g., {@code 2019-12-10T14:00}).
 * <p>
 * If parsing fails, throws an {@link AddException}.
 */
public class DateTimeUtil {

    /**
     * Parses a string into a {@link LocalDateTime}.
     * <p>
     * The method first attempts ISO-8601 parsing, then tries date-time formats,
     * and finally date-only formats (defaulting the time to 00:00).
     *
     * @param s the input string representing a date or date-time
     * @return the parsed {@link LocalDateTime}
     * @throws AddException if the input cannot be parsed in any supported format
     */
    public static LocalDateTime parse(String s) throws AddException {
        s = s.trim();
        DateTimeFormatter[] formats = new DateTimeFormatter[]{
                // Date + time
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("yyyy/M/d HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("d-M-yyyy HHmm"),
                DateTimeFormatter.ofPattern("d MMM yyyy HHmm"),
                DateTimeFormatter.ofPattern("MMM d yyyy HHmm"),

                // Date only
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("yyyy/M/d"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("d-M-yyyy"),
                DateTimeFormatter.ofPattern("d MMM yyyy"),
                DateTimeFormatter.ofPattern("MMM d yyyy"),
        };

        String in = s.trim().replaceAll("\\s+", " ");

        // Try strict ISO LocalDateTime
        try {
            return LocalDateTime.parse(in);
        } catch (DateTimeParseException ignored) {
            System.out.println(ignored.getMessage());
        }

        // Try date+time formats
        for (DateTimeFormatter f : formats) {
            try {
                return LocalDateTime.parse(in, f);
            } catch (DateTimeParseException ignored) {
                System.out.println(ignored.getMessage());
            }
        }

        // Try date-only; default time = 00:00
        for (DateTimeFormatter f : formats) {
            try {
                return LocalDate.parse(in, f).atStartOfDay();
            } catch (DateTimeParseException ignored) {
                System.out.println(ignored.getMessage());
            }
        }

        throw new AddException(
                "Invalid date/time. Try yyyy-MM-dd or d/M/yyyy, optionally with HHmm (e.g., 2019-12-02 1800)."
        );
    }
}
