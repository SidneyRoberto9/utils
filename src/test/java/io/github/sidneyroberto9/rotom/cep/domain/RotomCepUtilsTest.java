package io.github.sidneyroberto9.rotom.cep.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomCepUtilsTest {

    private final RotomCepUtils cepUtils = new RotomCepUtils();

    @Test
    void normalizeRemovesNonDigitsAndPassesThroughNull() {
        assertEquals("58038000", cepUtils.normalize("58038-000"));
        assertNull(cepUtils.normalize(null));
    }

    @Test
    void isValidAcceptsExactlyEightDigits() {
        assertTrue(cepUtils.isValid("58038000"));
        assertFalse(cepUtils.isValid("5803800"));
        assertFalse(cepUtils.isValid("58038-000"));
        assertFalse(cepUtils.isValid(null));
    }

    @Test
    void formatNormalizesAndAppliesStandardMask() {
        assertEquals("58038-000", cepUtils.format("58038000"));
        assertEquals("58038-000", cepUtils.format("58038-000"));
    }

    @Test
    void formatThrowsForInvalidCep() {
        assertThrows(IllegalArgumentException.class, () -> cepUtils.format("123"));
    }
}
