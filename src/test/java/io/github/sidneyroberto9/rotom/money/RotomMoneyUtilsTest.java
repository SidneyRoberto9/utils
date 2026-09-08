package io.github.sidneyroberto9.rotom.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomMoneyUtilsTest {

    private final RotomMoneyUtils money = new RotomMoneyUtils();

    @Test
    void formatBRLFromDoubleUsesCurrencySymbolAndCommaDecimal() {
        String formatted = money.formatBRL(1234.56);

        assertTrue(formatted.contains("1.234,56"));
        assertTrue(formatted.startsWith("R$"));
    }

    @Test
    void formatBRLFromBigDecimalUsesCurrencySymbolAndCommaDecimal() {
        String formatted = money.formatBRL(new BigDecimal("1234.56"));

        assertTrue(formatted.contains("1.234,56"));
        assertTrue(formatted.startsWith("R$"));
    }

    @Test
    void formatBRLRoundsToTwoDecimalPlaces() {
        assertEquals(money.formatBRL(10.0), money.formatBRL(10.001));
    }
}
