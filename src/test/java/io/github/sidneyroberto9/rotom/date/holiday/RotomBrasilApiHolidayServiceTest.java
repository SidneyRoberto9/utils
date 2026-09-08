package io.github.sidneyroberto9.rotom.date.holiday;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomBrasilApiHolidayServiceTest {

    private MockWebServer server;

    @BeforeEach
    void startServer() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    void stopServer() throws IOException {
        server.shutdown();
    }

    @Test
    void getHolidaysParsesDatesFromResponse() throws IOException {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(
                "[{\"date\":\"2026-01-01\",\"name\":\"Confraternização Universal\",\"type\":\"national\"},"
                        + "{\"date\":\"2026-12-25\",\"name\":\"Natal\",\"type\":\"national\"}]"
        ));
        RotomBrasilApiHolidayService service = new RotomBrasilApiHolidayService(server.url("/api/feriados/v1/").toString());

        Set<LocalDate> holidays = service.getHolidays(2026);

        assertEquals(2, holidays.size());
        assertTrue(holidays.contains(LocalDate.of(2026, 1, 1)));
        assertTrue(holidays.contains(LocalDate.of(2026, 12, 25)));
    }

    @Test
    void getHolidaysThrowsOnHttpError() {
        server.enqueue(new MockResponse().setResponseCode(500));
        RotomBrasilApiHolidayService service = new RotomBrasilApiHolidayService(server.url("/api/feriados/v1/").toString());

        assertThrows(IOException.class, () -> service.getHolidays(2026));
    }
}
