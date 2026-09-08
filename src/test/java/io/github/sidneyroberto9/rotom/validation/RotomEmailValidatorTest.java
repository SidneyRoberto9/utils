package io.github.sidneyroberto9.rotom.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomEmailValidatorTest {

    private final RotomEmailValidator email = new RotomEmailValidator();

    @Test
    void isValidAcceptsWellFormedAddresses() {
        assertTrue(email.isValid("user@example.com"));
        assertTrue(email.isValid("user.name+tag@sub.example.com"));
    }

    @Test
    void isValidTrimsSurroundingWhitespace() {
        assertTrue(email.isValid("  user@example.com  "));
    }

    @Test
    void isValidRejectsMalformedAddresses() {
        assertFalse(email.isValid("not-an-email"));
        assertFalse(email.isValid("user@"));
        assertFalse(email.isValid("@example.com"));
        assertFalse(email.isValid("user@example"));
    }

    @Test
    void isValidRejectsNull() {
        assertFalse(email.isValid(null));
    }
}
