package io.github.sidneyroberto9.rotom.encoding;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RotomEncodingUtilsTest {

    private final RotomEncodingUtils encoding = new RotomEncodingUtils();

    @Test
    void toBase64AndFromBase64RoundTrip() {
        String encoded = encoding.toBase64("rotom");

        assertEquals("cm90b20=", encoded);
        assertEquals("rotom", encoding.fromBase64(encoded));
    }

    @Test
    void fromBase64ThrowsOnInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> encoding.fromBase64("not-valid-base64!"));
    }

    @Test
    void toBase64ThrowsOnNull() {
        assertThrows(NullPointerException.class, () -> encoding.toBase64(null));
    }

    @Test
    void encodeIntAndDecodeIntRoundTrip() {
        String encoded = encoding.encodeInt(42);

        assertEquals(42, encoding.decodeInt(encoded));
    }

    @Test
    void decodeIntThrowsWhenDecodedLengthIsNotFourBytes() {
        String threeByteValue = encoding.toBase64("abc");

        assertThrows(IllegalArgumentException.class, () -> encoding.decodeInt(threeByteValue));
    }
}
