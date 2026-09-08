package io.github.sidneyroberto9.rotom.cnpj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomCNPJServiceTest {

    private final RotomCNPJService cnpj = new RotomCNPJService();

    @Test
    void isValidAcceptsMaskedAndUnmaskedValidCnpj() {
        assertTrue(cnpj.isValid("11.222.333/0001-81"));
        assertTrue(cnpj.isValid("11222333000181"));
    }

    @Test
    void isValidRejectsRepeatedDigitsWrongLengthAndNull() {
        assertFalse(cnpj.isValid("11.111.111/1111-11"));
        assertFalse(cnpj.isValid("123456"));
        assertFalse(cnpj.isValid(null));
    }

    @Test
    void isValidRejectsWrongCheckDigits() {
        assertFalse(cnpj.isValid("11.222.333/0001-80"));
    }

    @Test
    void formatAppliesStandardMask() {
        assertEquals("11.222.333/0001-81", cnpj.format("11222333000181"));
        assertEquals("11.222.333/0001-81", cnpj.format("11.222.333/0001-81"));
    }

    @Test
    void stripRemovesMaskAndPassesThroughNull() {
        assertEquals("11222333000181", cnpj.strip("11.222.333/0001-81"));
        assertNull(cnpj.strip(null));
    }

    @Test
    void isFormattedDetectsStandardPatternOnly() {
        assertTrue(cnpj.isFormatted("11.222.333/0001-81"));
        assertFalse(cnpj.isFormatted("11222333000181"));
        assertFalse(cnpj.isFormatted(null));
    }
}
