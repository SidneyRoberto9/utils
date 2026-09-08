package io.github.sidneyroberto9.rotom.http;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class for extracting information from incoming HTTP requests. Requires
 * {@code jakarta.servlet-api} on the classpath (already present in any Spring Boot 3 web
 * application).
 */
public class RotomHttpUtils {

    private static final String FORWARDED_FOR_HEADER = "X-Forwarded-For";

    /**
     * Resolves the real client IP address: prefers the first entry of the
     * {@code X-Forwarded-For} header and falls back to {@link HttpServletRequest#getRemoteAddr()}
     * when the header is absent, blank, or {@code "unknown"}.
     *
     * @param request incoming HTTP request
     * @return resolved client IP address
     */
    public String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader(FORWARDED_FOR_HEADER);

        if (forwardedFor != null && !forwardedFor.isBlank() && !"unknown".equalsIgnoreCase(forwardedFor)) {
            return forwardedFor.split(",")[0].trim();
        }

        return request.getRemoteAddr();
    }
}
