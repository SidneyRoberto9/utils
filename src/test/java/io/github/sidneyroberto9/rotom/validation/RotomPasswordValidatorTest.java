package io.github.sidneyroberto9.rotom.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomPasswordValidatorTest {

    private final RotomPasswordValidator password = new RotomPasswordValidator();

    @Test
    void isStrongAcceptsPasswordMeetingAllRequirements() {
        assertTrue(password.isStrong("Abcdef1@"));
    }

    @Test
    void isStrongRejectsMissingCharacterClasses() {
        assertFalse(password.isStrong("abcdefg1"));
        assertFalse(password.isStrong("ABCDEFG1"));
        assertFalse(password.isStrong("Abcdefgh"));
        assertFalse(password.isStrong("abcdefg@"));
    }

    @Test
    void isStrongRejectsWhitespaceAndOutOfRangeLength() {
        assertFalse(password.isStrong("Abc 123@"));
        assertFalse(password.isStrong("Ab1@"));
        assertFalse(password.isStrong("Abcdefghijklm123@"));
    }

    @Test
    void isStrongRejectsNull() {
        assertFalse(password.isStrong(null));
    }
}
