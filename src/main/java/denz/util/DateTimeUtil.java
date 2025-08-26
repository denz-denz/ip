package denz.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import denz.exception.AddException;
public class DateTimeUtil {
    public static LocalDateTime parse(String s) throws AddException {
        s = s.trim();
        DateTimeFormatter[] formats = new DateTimeFormatter[]{
                //Date + time
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

        // Also accept strict ISO LocalDateTime like 2019-12-10T14:00
        try {
            return LocalDateTime.parse(in);
        } catch (DateTimeParseException ignored) {}

        // Try date+time formats first
        for (DateTimeFormatter f : formats) {
            try {
                return LocalDateTime.parse(in, f);
            } catch (DateTimeParseException ignored) {}
        }

        // Then date-only; default time = 00:00
        for (DateTimeFormatter f : formats) {
            try {
                return LocalDate.parse(in, f).atStartOfDay(); }
            catch (DateTimeParseException ignored) {}
        }

        throw new AddException("Invalid date/time. Try yyyy-MM-dd or d/M/yyyy, optionally with HHmm (e.g., 2019-12-02 1800).");
    }
}

