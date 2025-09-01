package denz.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import denz.exception.AddException;

public class DateTimeUtilTest {
    /* ------------ ISO default ------------- */

    @Test
    void parsesIsoLocalDateTimeTseparator() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("2019-12-10T14:00");
        assertEquals(LocalDateTime.of(2019, 12, 10, 14, 0), dt);
    }

    /* ------------ Date + time patterns ------------- */

    @Test
    void parsesDashWithTimeHHmm() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("2019-12-10 1400");
        assertEquals(LocalDateTime.of(2019, 12, 10, 14, 0), dt);
    }

    @Test
    void parsesSlashWithTimeHHmm() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("1/12/2019 0930");
        assertEquals(LocalDateTime.of(2019, 12, 1, 9, 30), dt);
    }

    @Test
    void parsesDashDmyWithTime() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("1-12-2019 2359");
        assertEquals(LocalDateTime.of(2019, 12, 1, 23, 59), dt);
    }

    @Test
    void parsesDMmmYyyyWithTime() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("2 Dec 2019 0800");
        assertEquals(LocalDateTime.of(2019, 12, 2, 8, 0), dt);
    }

    @Test
    void parsesMmmDYyyyWithTime() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("Dec 2 2019 0800");
        assertEquals(LocalDateTime.of(2019, 12, 2, 8, 0), dt);
    }

    /* ------------ Date-only patterns (defaults to 00:00) ------------- */

    @Test
    void parsesDateOnlyDashDefaultsMidnight() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("2019-12-10");
        assertEquals(LocalDate.of(2019, 12, 10).atStartOfDay(), dt);
    }

    @Test
    void parsesDateOnlySlashDefaultsMidnight() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("2/12/2019");
        assertEquals(LocalDate.of(2019, 12, 2).atStartOfDay(), dt);
    }

    @Test
    void parsesDateOnlyDefaultsMidnight() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("Dec 2 2019");
        assertEquals(LocalDate.of(2019, 12, 2).atStartOfDay(), dt);
    }

    /* ------------ Whitespace normalization ------------- */

    @Test
    void trimsAndCollapsesSpaces() throws AddException {
        LocalDateTime dt = DateTimeUtil.parse("   2019-12-10    1400  ");
        assertEquals(LocalDateTime.of(2019, 12, 10, 14, 0), dt);
    }

    /* ------------ Invalid inputs ------------- */

    @Test
    void invalidStringThrows() {
        assertThrows(denz.exception.AddException.class, () -> DateTimeUtil.parse("not-a-date"));
    }

    @Test
    void impossibleDateThrows() {
        assertThrows(denz.exception.AddException.class, () -> DateTimeUtil.parse("2019 Dec 2"));
        assertThrows(denz.exception.AddException.class, () -> DateTimeUtil.parse("1800 2019/12/02"));
    }
}
