package io.github.sidneyroberto9.rotom.sms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RotomGsm7ConverterTest {

    private final RotomGsm7Converter gsm7 = new RotomGsm7Converter();

    @Test
    void convertToGsm7KeepsCharsAlreadyInCharset() {
        assertEquals("Hello, World! 123", gsm7.convertToGsm7("Hello, World! 123"));
    }

    @Test
    void convertToGsm7FoldsAccentedCharactersToAscii() {
        assertEquals("sofa", gsm7.convertToGsm7("sofá"));
        assertEquals("coracao", gsm7.convertToGsm7("coração"));
    }

    @Test
    void convertToGsm7DropsUnsupportedCharacters() {
        assertEquals("Hi ", gsm7.convertToGsm7("Hi 😀"));
    }

    @Test
    void convertToGsm7ThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> gsm7.convertToGsm7(null));
    }
}
