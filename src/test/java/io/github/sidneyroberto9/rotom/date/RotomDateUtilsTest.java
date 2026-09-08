package io.github.sidneyroberto9.rotom.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomDateUtilsTest {

    private final RotomDateUtils dateUtils = new RotomDateUtils();
    private final ZoneId zone = ZoneId.of("America/Sao_Paulo");

    @Test
    void toLocalDateAndBackRoundTripThroughZone() {
        LocalDate date = LocalDate.of(2025, 6, 9);
        Date legacy = dateUtils.toDate(date, zone);

        assertEquals(date, dateUtils.toLocalDate(legacy, zone));
    }

    @Test
    void toLocalDateTimeAndBackRoundTripThroughZone() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 9, 14, 30);
        Date legacy = dateUtils.toDate(dateTime, zone);

        assertEquals(dateTime, dateUtils.toLocalDateTime(legacy, zone));
    }

    @Test
    void isWeekendDetectsSaturdayAndSunday() {
        assertTrue(dateUtils.isWeekend(LocalDate.of(2025, 6, 7)));
        assertTrue(dateUtils.isWeekend(LocalDate.of(2025, 6, 8)));
        assertFalse(dateUtils.isWeekend(LocalDate.of(2025, 6, 9)));
    }

    @Test
    void isWeekDayIsOppositeOfIsWeekend() {
        assertTrue(dateUtils.isWeekDay(LocalDate.of(2025, 6, 9)));
        assertFalse(dateUtils.isWeekDay(LocalDate.of(2025, 6, 7)));
    }

    @Test
    void isTodayDetectsCurrentDateOnly() {
        assertTrue(dateUtils.isToday(LocalDate.now()));
        assertFalse(dateUtils.isToday(LocalDate.now().minusDays(1)));
    }

    @Test
    void isPastAndIsFutureCompareAgainstNow() {
        assertTrue(dateUtils.isPast(LocalDate.now().minusDays(1)));
        assertFalse(dateUtils.isPast(LocalDate.now().plusDays(1)));
        assertTrue(dateUtils.isFuture(LocalDate.now().plusDays(1)));
        assertFalse(dateUtils.isFuture(LocalDate.now().minusDays(1)));
    }

    @Test
    void isWithinDateWindowChecksDayOfMonthRangeInclusive() {
        LocalDate date = LocalDate.of(2025, 6, 15);

        assertTrue(dateUtils.isWithinDateWindow(date, 10, 20));
        assertTrue(dateUtils.isWithinDateWindow(date, 15, 15));
        assertFalse(dateUtils.isWithinDateWindow(date, 16, 20));
    }

    @Test
    void formatDateUsesBrazilianPattern() {
        assertEquals("09/06/2025", dateUtils.formatDate(LocalDate.of(2025, 6, 9)));
    }

    @Test
    void formatDateTimeUsesBrazilianPatternWithTime() {
        assertEquals("09/06/2025 14:30", dateUtils.formatDateTime(LocalDateTime.of(2025, 6, 9, 14, 30)));
    }

    @Test
    void formatMonthYearPadsSingleDigitMonth() {
        assertEquals("06/2025", dateUtils.formatMonthYear(6, 2025));
        assertEquals("12/2025", dateUtils.formatMonthYear(12, 2025));
    }

    @Test
    void semesterKeySplitsYearAtJune() {
        assertEquals("2025-01", dateUtils.semesterKey(LocalDate.of(2025, 5, 31)));
        assertEquals("2025-06", dateUtils.semesterKey(LocalDate.of(2025, 6, 1)));
        assertEquals("2025-06", dateUtils.semesterKey(LocalDate.of(2025, 12, 31)));
    }
}
