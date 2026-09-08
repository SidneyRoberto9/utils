package io.github.sidneyroberto9.rotom;

import io.github.sidneyroberto9.rotom.crypto.RotomCryptoUtils;
import io.github.sidneyroberto9.rotom.hash.RotomHashUtils;
import io.github.sidneyroberto9.rotom.io.RotomFileUtils;
import io.github.sidneyroberto9.rotom.random.RotomRandomUtil;
import io.github.sidneyroberto9.rotom.strings.RotomStringUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke checks for the features extracted from m4all's eleva-docs, eleva-helpdesk-api,
 * eleva-management-api, eleva_supplies_api and homeseg-api duplicated utility code.
 */
class RotomExtractedFeaturesTest {

    @Test
    void titleCasePtLowersConnectivesAndKeepsAcronyms() {
        RotomStringUtils strings = new RotomStringUtils();

        assertEquals(
                "Termo de Responsabilidade para Uso de EPI",
                strings.titleCasePt("TERMO DE RESPONSABILIDADE PARA USO DE EPI", Map.of("epi", "EPI"))
        );
    }

    @Test
    void titleCasePtFallsBackToSentenceCaseWhenEndingInPunctuation() {
        RotomStringUtils strings = new RotomStringUtils();

        assertEquals("Chamado encerrado pelo usuário.", strings.titleCasePt("CHAMADO ENCERRADO PELO USUÁRIO."));
    }

    @Test
    void normalizeUfAndIsValidUf() {
        RotomStringUtils strings = new RotomStringUtils();

        assertEquals("SP", strings.normalizeUf("  sp  "));
        assertEquals("RJ", strings.normalizeUf("rj"));
        assertTrue(strings.isValidUf(" sp "));
        assertFalse(strings.isValidUf("XX"));
        assertFalse(strings.isValidUf(null));
        assertNull(strings.normalizeUf(null));
    }

    @Test
    void sanitizeFileNameKeepsOnlySafeCharacters() {
        RotomFileUtils files = new RotomFileUtils();
        String sanitized = files.sanitizeFileName("relatório (final) v2!.pdf");

        assertTrue(sanitized.matches("[a-zA-Z0-9._-]+"));
        assertTrue(sanitized.endsWith(".pdf"));
        assertNull(files.sanitizeFileName(null));
    }

    @Test
    void uniqueFileNameAppendsExtensionAndVaries() {
        RotomFileUtils files = new RotomFileUtils();

        assertTrue(files.uniqueFileName("pdf").endsWith(".pdf"));
        assertNotEquals(files.uniqueFileName("pdf"), files.uniqueFileName("pdf"));
    }

    @Test
    void ensureDirectoryExistsCreatesMissingDirectory() throws IOException {
        RotomFileUtils files = new RotomFileUtils();
        Path dir = Files.createTempDirectory("rotom-test").resolve("nested/child");

        files.ensureDirectoryExists(dir.toString());

        assertTrue(Files.isDirectory(dir));
    }

    @Test
    void hashMatchesComparesCaseInsensitively() {
        RotomHashUtils hash = new RotomHashUtils();
        String sha256 = hash.sha256("rotom".getBytes(StandardCharsets.UTF_8));

        assertTrue(hash.matches("rotom", sha256.toUpperCase(), "SHA-256"));
        assertFalse(hash.matches("rotom", "deadbeef", "SHA-256"));
        assertFalse(hash.matches("rotom", null, "SHA-256"));
    }

    @Test
    void verifyHmacAcceptsPrefixedSignatureAndRejectsTampering() throws Exception {
        RotomHashUtils hash = new RotomHashUtils();
        String payload = "{\"event\":\"ping\"}";
        String secret = "shh";
        String computed = this.hmacSha256Hex(payload, secret);

        assertTrue(hash.verifyHmac(payload, "sha256=" + computed, secret, "HmacSHA256"));
        assertTrue(hash.verifyHmac(payload, computed, secret, "HmacSHA256"));
        assertFalse(hash.verifyHmac(payload + "tampered", "sha256=" + computed, secret, "HmacSHA256"));
        assertFalse(hash.verifyHmac(null, computed, secret, "HmacSHA256"));
    }

    @Test
    void randomHexProducesTwiceTheByteLength() {
        RotomRandomUtil random = new RotomRandomUtil();

        assertEquals(64, random.randomHex(32).length());
        assertThrows(IllegalArgumentException.class, () -> random.randomHex(0));
    }

    @Test
    void encryptDecryptRoundTrip() {
        RotomCryptoUtils crypto = new RotomCryptoUtils();
        String key = Base64.getEncoder().encodeToString("0123456789abcdef".getBytes(StandardCharsets.UTF_8));

        String ciphertext = crypto.encrypt("segredo-do-totp", key);

        assertNotEquals("segredo-do-totp", ciphertext);
        assertEquals("segredo-do-totp", crypto.decrypt(ciphertext, key));
    }

    @Test
    void decryptFailsWithWrongKey() {
        RotomCryptoUtils crypto = new RotomCryptoUtils();
        String key = Base64.getEncoder().encodeToString("0123456789abcdef".getBytes(StandardCharsets.UTF_8));
        String otherKey = Base64.getEncoder().encodeToString("fedcba9876543210".getBytes(StandardCharsets.UTF_8));

        String ciphertext = crypto.encrypt("segredo", key);

        assertThrows(IllegalArgumentException.class, () -> crypto.decrypt(ciphertext, otherKey));
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
