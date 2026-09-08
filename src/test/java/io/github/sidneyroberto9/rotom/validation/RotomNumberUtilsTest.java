package io.github.sidneyroberto9.rotom.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomNumberUtilsTest {

    private final RotomNumberUtils numbers = new RotomNumberUtils();

    @Test
    void isIntegerAcceptsIntegersOnly() {
        assertTrue(numbers.isInteger("123"));
        assertTrue(numbers.isInteger("-123"));
        assertFalse(numbers.isInteger("1.5"));
        assertFalse(numbers.isInteger(""));
        assertFalse(numbers.isInteger(null));
    }

    @Test
    void isFloatAcceptsFloatingPointValues() {
        assertTrue(numbers.isFloat("1.5"));
        assertTrue(numbers.isFloat("123"));
        assertFalse(numbers.isFloat("abc"));
        assertFalse(numbers.isFloat(null));
    }

    @Test
    void isDoubleAcceptsFloatingPointValues() {
        assertTrue(numbers.isDouble("1.5"));
        assertTrue(numbers.isDouble("123"));
        assertFalse(numbers.isDouble("abc"));
        assertFalse(numbers.isDouble(null));
    }

    @Test
    void isNumericMatchesPlainNumericPatternOnly() {
        assertTrue(numbers.isNumeric("123"));
        assertTrue(numbers.isNumeric("-123.45"));
        assertFalse(numbers.isNumeric("1e10"));
        assertFalse(numbers.isNumeric(""));
        assertFalse(numbers.isNumeric(null));
    }
}
