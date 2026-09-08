package io.github.sidneyroberto9.rotom.mask;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RotomMaskUtilsTest {

    private final RotomMaskUtils mask = new RotomMaskUtils();

    @Test
    void maskCpfRevealsFirstThreeAndLastTwoDigits() {
        assertEquals("123.***.***-09", mask.maskCpf("12345678909"));
        assertEquals("123.***.***-09", mask.maskCpf("123.456.789-09"));
    }

    @Test
    void maskCpfReturnsAllStarsForWrongLength() {
        assertEquals("***********", mask.maskCpf("123"));
    }

    @Test
    void maskCpfReturnsNullForNull() {
        assertNull(mask.maskCpf(null));
    }

    @Test
    void maskCnpjRevealsFirstTwoDigitsAndBranchBlock() {
        assertEquals("12.***.***/0001-**", mask.maskCnpj("12345678000195"));
        assertEquals("12.***.***/0001-**", mask.maskCnpj("12.345.678/0001-95"));
    }

    @Test
    void maskCnpjReturnsAllStarsForWrongLength() {
        assertEquals("**************", mask.maskCnpj("123"));
    }

    @Test
    void maskCnpjReturnsNullForNull() {
        assertNull(mask.maskCnpj(null));
    }
}
