package io.github.sidneyroberto9.rotom.crypto;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RotomCryptoUtilsTest {

    private final RotomCryptoUtils crypto = new RotomCryptoUtils();
    private final String key = Base64.getEncoder().encodeToString("0123456789abcdef".getBytes(StandardCharsets.UTF_8));

    @Test
    void encryptDecryptRoundTrip() {
        String ciphertext = crypto.encrypt("segredo-do-totp", key);

        assertNotEquals("segredo-do-totp", ciphertext);
        assertEquals("segredo-do-totp", crypto.decrypt(ciphertext, key));
    }

    @Test
    void encryptProducesDifferentCiphertextEachTimeDueToRandomIv() {
        assertNotEquals(crypto.encrypt("segredo", key), crypto.encrypt("segredo", key));
    }

    @Test
    void decryptFailsWithWrongKey() {
        String otherKey = Base64.getEncoder().encodeToString("fedcba9876543210".getBytes(StandardCharsets.UTF_8));
        String ciphertext = crypto.encrypt("segredo", key);

        assertThrows(IllegalArgumentException.class, () -> crypto.decrypt(ciphertext, otherKey));
    }

    @Test
    void decryptFailsWithTamperedCiphertext() {
        String ciphertext = crypto.encrypt("segredo", key);
        String tampered = ciphertext.substring(0, ciphertext.length() - 4) + "abcd";

        assertThrows(IllegalArgumentException.class, () -> crypto.decrypt(tampered, key));
    }

    @Test
    void encryptThrowsForInvalidKey() {
        assertThrows(IllegalArgumentException.class, () -> crypto.encrypt("segredo", "not-valid-base64!"));
    }

    @Test
    void encryptThrowsForWrongKeyLength() {
        String shortKey = Base64.getEncoder().encodeToString("short".getBytes(StandardCharsets.UTF_8));

        assertThrows(IllegalArgumentException.class, () -> crypto.encrypt("segredo", shortKey));
    }
}
