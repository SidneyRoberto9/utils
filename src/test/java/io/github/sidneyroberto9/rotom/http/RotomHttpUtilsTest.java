package io.github.sidneyroberto9.rotom.http;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RotomHttpUtilsTest {

    private final RotomHttpUtils httpUtils = new RotomHttpUtils();

    @Test
    void getClientIpPrefersFirstEntryOfForwardedForHeader() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-Forwarded-For")).thenReturn("203.0.113.5, 10.0.0.1");

        assertEquals("203.0.113.5", httpUtils.getClientIp(request));
    }

    @Test
    void getClientIpTrimsWhitespaceAroundFirstEntry() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-Forwarded-For")).thenReturn("  203.0.113.5  , 10.0.0.1");

        assertEquals("203.0.113.5", httpUtils.getClientIp(request));
    }

    @Test
    void getClientIpFallsBackToRemoteAddrWhenHeaderIsAbsent() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("192.168.0.10");

        assertEquals("192.168.0.10", httpUtils.getClientIp(request));
    }

    @Test
    void getClientIpFallsBackToRemoteAddrWhenHeaderIsBlankOrUnknown() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRemoteAddr()).thenReturn("192.168.0.10");

        when(request.getHeader("X-Forwarded-For")).thenReturn("   ");
        assertEquals("192.168.0.10", httpUtils.getClientIp(request));

        when(request.getHeader("X-Forwarded-For")).thenReturn("unknown");
        assertEquals("192.168.0.10", httpUtils.getClientIp(request));
    }
}
