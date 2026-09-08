package io.github.sidneyroberto9.rotom.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RotomDurationUtilsTest {

    private final RotomDurationUtils duration = new RotomDurationUtils();

    @Test
    void daysBetweenCountsWholeDays() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 4, 12, 0);

        assertEquals(3, duration.daysBetween(start, end));
    }

    @Test
    void hoursBetweenCountsWholeHours() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 1, 11, 30);

        assertEquals(3, duration.hoursBetween(start, end));
    }

    @Test
    void humanizeHoursAndMinutesReturnsMinutesOnlyUnderAnHour() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 1, 8, 45);

        assertEquals("45 minutos", duration.humanizeHoursAndMinutes(start, end));
    }

    @Test
    void humanizeHoursAndMinutesReturnsHoursAndMinutes() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 1, 10, 15);

        assertEquals("2 hora(s) e 15 minuto(s)", duration.humanizeHoursAndMinutes(start, end));
    }

    @Test
    void humanizeDaysHoursAndMinutesReturnsMinutesOnlyUnderAnHour() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 1, 8, 30);

        assertEquals("30 minutos", duration.humanizeDaysHoursAndMinutes(start, end));
    }

    @Test
    void humanizeDaysHoursAndMinutesReturnsHoursAndMinutesUnderADay() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 1, 11, 20);

        assertEquals("3 hora(s) e 20 minuto(s)", duration.humanizeDaysHoursAndMinutes(start, end));
    }

    @Test
    void humanizeDaysHoursAndMinutesReturnsDaysHoursAndMinutes() {
        LocalDateTime start = LocalDateTime.of(2025, 6, 1, 8, 0);
        LocalDateTime end = LocalDateTime.of(2025, 6, 2, 11, 20);

        assertEquals("1 dia(s) 3 hora(s) e 20 minuto(s)", duration.humanizeDaysHoursAndMinutes(start, end));
    }
}
