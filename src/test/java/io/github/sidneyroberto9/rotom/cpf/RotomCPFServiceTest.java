package io.github.sidneyroberto9.rotom.cpf;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomCPFServiceTest {

    private final RotomCPFService cpf = new RotomCPFService();

    @Test
    void isValidAcceptsMaskedAndUnmaskedValidCpf() {
        assertTrue(cpf.isValid("529.982.247-25"));
        assertTrue(cpf.isValid("52998224725"));
    }

    @Test
    void isValidRejectsRepeatedDigitsWrongLengthAndNull() {
        assertFalse(cpf.isValid("111.111.111-11"));
        assertFalse(cpf.isValid("123456"));
        assertFalse(cpf.isValid(null));
    }

    @Test
    void isValidRejectsWrongCheckDigits() {
        assertFalse(cpf.isValid("529.982.247-24"));
    }

    @Test
    void formatAppliesStandardMask() {
        assertEquals("529.982.247-25", cpf.format("52998224725"));
        assertEquals("529.982.247-25", cpf.format("529.982.247-25"));
    }

    @Test
    void stripRemovesMaskAndPassesThroughNull() {
        assertEquals("52998224725", cpf.strip("529.982.247-25"));
        assertNull(cpf.strip(null));
    }

    @Test
    void isFormattedDetectsStandardPatternOnly() {
        assertTrue(cpf.isFormatted("529.982.247-25"));
        assertFalse(cpf.isFormatted("52998224725"));
        assertFalse(cpf.isFormatted(null));
    }
}
