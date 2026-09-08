package io.github.sidneyroberto9.rotom.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomDateServiceTest {

    private final RotomDateService dateService = new RotomDateService();

    @Test
    void isHolidayRecognizesBrazilianNationalHoliday() {
        assertTrue(dateService.isHoliday(LocalDate.of(2025, 1, 1)));
        assertFalse(dateService.isHoliday(LocalDate.of(2025, 2, 3)));
    }

    @Test
    void isBusinessDayRejectsWeekendsAndHolidays() {
        assertTrue(dateService.isBusinessDay(LocalDate.of(2025, 2, 3)));
        assertFalse(dateService.isBusinessDay(LocalDate.of(2025, 2, 1)));
        assertFalse(dateService.isBusinessDay(LocalDate.of(2025, 1, 1)));
    }

    @Test
    void addBusinessDaysSkipsWeekends() {
        assertEquals(LocalDate.of(2025, 2, 7), dateService.addBusinessDays(LocalDate.of(2025, 2, 3), 4));
        assertEquals(LocalDate.of(2025, 2, 10), dateService.addBusinessDays(LocalDate.of(2025, 2, 7), 1));
    }

    @Test
    void addBusinessDaysWithZeroReturnsSameDate() {
        LocalDate date = LocalDate.of(2025, 2, 3);

        assertEquals(date, dateService.addBusinessDays(date, 0));
    }

    @Test
    void subtractBusinessDaysMirrorsAddBusinessDays() {
        assertEquals(LocalDate.of(2025, 2, 7), dateService.subtractBusinessDays(LocalDate.of(2025, 2, 10), 1));
    }

    @Test
    void subtractBusinessDaysThrowsOnNegativeDays() {
        assertThrows(IllegalArgumentException.class,
                () -> dateService.subtractBusinessDays(LocalDate.of(2025, 2, 10), -1));
    }

    @Test
    void adjustToNextBusinessDaySkipsWeekend() {
        assertEquals(LocalDate.of(2025, 2, 10), dateService.adjustToNextBusinessDay(LocalDate.of(2025, 2, 8)));
    }

    @Test
    void adjustToNextBusinessDayReturnsSameDateWhenAlreadyBusinessDay() {
        LocalDate date = LocalDate.of(2025, 2, 3);

        assertEquals(date, dateService.adjustToNextBusinessDay(date));
    }

    @Test
    void adjustToPreviousBusinessDaySkipsWeekend() {
        assertEquals(LocalDate.of(2025, 2, 7), dateService.adjustToPreviousBusinessDay(LocalDate.of(2025, 2, 9)));
    }

    @Test
    void getFirstBusinessDayOfMonthSkipsLeadingWeekend() {
        assertEquals(LocalDate.of(2025, 2, 3), dateService.getFirstBusinessDayOfMonth(2025, 2));
    }

    @Test
    void getLastBusinessDayOfMonthReturnsLastDayWhenAlreadyBusinessDay() {
        assertEquals(LocalDate.of(2025, 2, 28), dateService.getLastBusinessDayOfMonth(2025, 2));
    }

    @Test
    void countBusinessDaysExcludesWeekendsAndEndDate() {
        assertEquals(5, dateService.countBusinessDays(LocalDate.of(2025, 2, 3), LocalDate.of(2025, 2, 10)));
    }

    @Test
    void countBusinessDaysThrowsWhenToIsBeforeFrom() {
        assertThrows(IllegalArgumentException.class,
                () -> dateService.countBusinessDays(LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 3)));
    }
}
