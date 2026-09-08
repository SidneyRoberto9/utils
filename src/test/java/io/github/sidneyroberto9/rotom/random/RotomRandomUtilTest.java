package io.github.sidneyroberto9.rotom.random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomRandomUtilTest {

    private final RotomRandomUtil random = new RotomRandomUtil();

    @Test
    void randomIntStaysWithinInclusiveRange() {
        for (int i = 0; i < 100; i++) {
            int value = random.randomInt(5, 10);
            assertTrue(value >= 5 && value <= 10);
        }
    }

    @Test
    void randomIntAllowsEqualMinAndMax() {
        assertEquals(7, random.randomInt(7, 7));
    }

    @Test
    void randomIntThrowsWhenMinGreaterThanMax() {
        assertThrows(IllegalArgumentException.class, () -> random.randomInt(10, 5));
    }

    @Test
    void randomAlphanumericProducesRequestedLengthAndCharset() {
        String value = random.randomAlphanumeric(16);

        assertEquals(16, value.length());
        assertTrue(value.matches("[A-Z0-9]+"));
    }

    @Test
    void randomNumericProducesRequestedLengthAndDigitsOnly() {
        String value = random.randomNumeric(8);

        assertEquals(8, value.length());
        assertTrue(value.matches("[0-9]+"));
    }

    @Test
    void randomCodeIsAliasOfRandomAlphanumeric() {
        String value = random.randomCode(10);

        assertEquals(10, value.length());
        assertTrue(value.matches("[A-Z0-9]+"));
    }

    @Test
    void randomFromMethodsThrowOnNegativeSize() {
        assertThrows(IllegalArgumentException.class, () -> random.randomAlphanumeric(-1));
        assertThrows(IllegalArgumentException.class, () -> random.randomNumeric(-1));
    }

    @Test
    void randomFromMethodsAllowZeroSize() {
        assertEquals("", random.randomAlphanumeric(0));
        assertEquals("", random.randomNumeric(0));
    }

    @Test
    void tokenTruncatesUuidToRequestedLength() {
        String value = random.token(16);

        assertEquals(16, value.length());
        assertTrue(value.matches("[0-9a-f]+"));
    }

    @Test
    void tokenThrowsWhenLimitOutOfRange() {
        assertThrows(IllegalArgumentException.class, () -> random.token(0));
        assertThrows(IllegalArgumentException.class, () -> random.token(33));
    }

    @Test
    void apiKeyUsesDefaultPrefixAndVaries() {
        String key = random.apiKey();

        assertTrue(key.startsWith("sk_live_"));
        assertEquals("sk_live_".length() + 12, key.length());
        assertNotEquals(random.apiKey(), random.apiKey());
    }

    @Test
    void apiKeyWithCustomPrefixUsesGivenPrefix() {
        String key = random.apiKey("test_");

        assertTrue(key.startsWith("test_"));
        assertEquals("test_".length() + 12, key.length());
    }

    @Test
    void randomHexProducesTwiceTheByteLengthAndVaries() {
        assertEquals(64, random.randomHex(32).length());
        assertTrue(random.randomHex(4).matches("[0-9a-f]+"));
        assertNotEquals(random.randomHex(16), random.randomHex(16));
    }

    @Test
    void randomHexThrowsOnNonPositiveByteLength() {
        assertThrows(IllegalArgumentException.class, () -> random.randomHex(0));
        assertThrows(IllegalArgumentException.class, () -> random.randomHex(-1));
    }
}
