package io.github.sidneyroberto9.rotom.hash;

import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RotomHashUtilsTest {

    private final RotomHashUtils hash = new RotomHashUtils();

    @Test
    void hashIsDeterministicAndConsistentBetweenBytesAndText() {
        String fromText = hash.hash("rotom", "SHA-256");
        String fromBytes = hash.hash("rotom".getBytes(StandardCharsets.UTF_8), "SHA-256");

        assertEquals(fromText, fromBytes);
        assertEquals(fromText, hash.hash("rotom", "SHA-256"));
    }

    @Test
    void hashThrowsForUnsupportedAlgorithm() {
        assertThrows(IllegalArgumentException.class, () -> hash.hash("rotom", "NOT-AN-ALGORITHM"));
    }

    @Test
    void hashFromStreamMatchesHashFromBytes() throws IOException {
        byte[] data = "rotom".getBytes(StandardCharsets.UTF_8);
        String fromStream = hash.hash(new ByteArrayInputStream(data), "SHA-256");

        assertEquals(hash.hash(data, "SHA-256"), fromStream);
    }

    @Test
    void hashFromFileMatchesHashFromBytes() throws IOException {
        byte[] data = "rotom".getBytes(StandardCharsets.UTF_8);
        Path file = Files.createTempFile("rotom-hash-test", ".txt");
        Files.write(file, data);

        assertEquals(hash.hash(data, "SHA-256"), hash.hash(file.toFile(), "SHA-256"));
    }

    @Test
    void hashFromMissingFileThrows() {
        assertThrows(IOException.class, () -> hash.hash(new File("does-not-exist.txt"), "SHA-256"));
    }

    @Test
    void md5Sha256Sha512DelegateToHashWithMatchingAlgorithmAndLength() {
        byte[] data = "rotom".getBytes(StandardCharsets.UTF_8);

        assertEquals(hash.hash(data, "MD5"), hash.md5(data));
        assertEquals(32, hash.md5(data).length());

        assertEquals(hash.hash(data, "SHA-256"), hash.sha256(data));
        assertEquals(64, hash.sha256(data).length());

        assertEquals(hash.hash(data, "SHA-512"), hash.sha512(data));
        assertEquals(128, hash.sha512(data).length());
    }

    @Test
    void matchesComparesCaseInsensitivelyAndRejectsNullOrWrongHash() {
        String sha256 = hash.sha256("rotom".getBytes(StandardCharsets.UTF_8));

        assertTrue(hash.matches("rotom", sha256.toUpperCase(), "SHA-256"));
        assertFalse(hash.matches("rotom", "deadbeef", "SHA-256"));
        assertFalse(hash.matches("rotom", null, "SHA-256"));
    }

    @Test
    void verifyHmacAcceptsPrefixedSignatureAndRejectsTampering() throws Exception {
        String payload = "{\"event\":\"ping\"}";
        String secret = "shh";
        String computed = this.hmacSha256Hex(payload, secret);

        assertTrue(hash.verifyHmac(payload, "sha256=" + computed, secret, "HmacSHA256"));
        assertTrue(hash.verifyHmac(payload, computed, secret, "HmacSHA256"));
        assertFalse(hash.verifyHmac(payload + "tampered", "sha256=" + computed, secret, "HmacSHA256"));
    }

    @Test
    void verifyHmacRejectsNullArguments() {
        assertFalse(hash.verifyHmac(null, "sig", "secret", "HmacSHA256"));
        assertFalse(hash.verifyHmac("payload", null, "secret", "HmacSHA256"));
        assertFalse(hash.verifyHmac("payload", "sig", null, "HmacSHA256"));
    }

    @Test
    void verifyHmacThrowsForUnsupportedAlgorithm() {
        assertThrows(IllegalArgumentException.class,
                () -> hash.verifyHmac("payload", "sig", "secret", "NOT-AN-ALGORITHM"));
    }

    private String hmacSha256Hex(String payload, String secret) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] raw = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder(raw.length * 2);

        for (byte b : raw) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }
}
