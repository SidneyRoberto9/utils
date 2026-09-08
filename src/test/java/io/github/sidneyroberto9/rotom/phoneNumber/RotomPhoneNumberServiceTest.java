package io.github.sidneyroberto9.rotom.phoneNumber;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomPhoneNumberServiceTest {

    private final RotomPhoneNumberService phone = new RotomPhoneNumberService();

    @Test
    void stripRemovesNonDigitsAndPassesThroughNull() {
        assertEquals("83986635812", phone.strip("(83) 98663-5812"));
        assertNull(phone.strip(null));
    }

    @Test
    void getDDDExtractsAreaCodeWhenPresent() {
        assertEquals("83", phone.getDDD("(83) 98663-5812"));
        assertNull(phone.getDDD("98663-5812"));
    }

    @Test
    void isMobileDetectsNinthDigitWithAndWithoutDdd() {
        assertTrue(phone.isMobile("83986635812"));
        assertTrue(phone.isMobile("986635812"));
        assertFalse(phone.isMobile("8332221234"));
        assertFalse(phone.isMobile(null));
    }

    @Test
    void isLandlineDetectsEightDigitLocalNumberWithAndWithoutDdd() {
        assertTrue(phone.isLandline("8332221234"));
        assertTrue(phone.isLandline("32221234"));
        assertFalse(phone.isLandline("83986635812"));
    }

    @Test
    void isFormattedDetectsMobileAndLandlinePatterns() {
        assertTrue(phone.isFormatted("(83) 98663-5812"));
        assertTrue(phone.isFormatted("(83) 3222-1234"));
        assertFalse(phone.isFormatted("83986635812"));
        assertFalse(phone.isFormatted(null));
    }

    @Test
    void isValidAcceptsKnownDddAndRejectsUnknownDdd() {
        assertTrue(phone.isValid("83986635812"));
        assertTrue(phone.isValid("8332221234"));
        assertTrue(phone.isValid("986635812"));
        assertFalse(phone.isValid("00986635812"));
        assertFalse(phone.isValid("123"));
    }

    @Test
    void formatDetectsMobileAndLandlineWithAndWithoutDdd() {
        assertEquals("(83) 98663-5812", phone.format("83986635812"));
        assertEquals("(83) 3222-1234", phone.format("8332221234"));
        assertEquals("98663-5812", phone.format("986635812"));
        assertEquals("3222-1234", phone.format("32221234"));
    }

    @Test
    void formatStripsCountryCodeWhenPresent() {
        assertEquals("(83) 98663-5812", phone.format("5583986635812"));
    }

    @Test
    void formatThrowsForUninterpretableNumber() {
        assertThrows(IllegalArgumentException.class, () -> phone.format("123"));
    }

    @Test
    void formatWithCountryCodePrependsPlus55() {
        assertEquals("+55 (83) 98663-5812", phone.formatWithCountryCode("83986635812"));
        assertEquals("+55 (83) 3222-1234", phone.formatWithCountryCode("8332221234"));
    }

    @Test
    void formatWithCountryCodeStripsExistingCountryCode() {
        assertEquals("+55 (83) 98663-5812", phone.formatWithCountryCode("5583986635812"));
    }

    @Test
    void formatWithCountryCodeThrowsWithoutAreaCode() {
        assertThrows(IllegalArgumentException.class, () -> phone.formatWithCountryCode("986635812"));
    }
}
