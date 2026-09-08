package io.github.sidneyroberto9.rotom.hash;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for computing cryptographic hashes (checksums) of byte arrays, strings, streams,
 * and files.
 */
public class RotomHashUtils {

    private static final int BUFFER_SIZE = 8192;

    /**
     * Computes the hash of the given bytes using the specified algorithm.
     *
     * @param data      bytes to hash
     * @param algorithm {@link MessageDigest} algorithm name (e.g. {@code MD5}, {@code SHA-256}, {@code SHA-512})
     * @return hash as a lowercase hexadecimal string
     * @throws IllegalArgumentException if {@code algorithm} is not supported by the JVM
     */
    public String hash(byte[] data, String algorithm) {
        MessageDigest digest = this.digestFor(algorithm);
        return this.toHex(digest.digest(data));
    }

    /**
     * Computes the hash of the given text (UTF-8) using the specified algorithm.
     *
     * @param text      text to hash
     * @param algorithm {@link MessageDigest} algorithm name (e.g. {@code MD5}, {@code SHA-256}, {@code SHA-512})
     * @return hash as a lowercase hexadecimal string
     * @throws IllegalArgumentException if {@code algorithm} is not supported by the JVM
     */
    public String hash(String text, String algorithm) {
        return this.hash(text.getBytes(StandardCharsets.UTF_8), algorithm);
    }

    /**
     * Computes the hash of the given stream's content using the specified algorithm.
     * The stream is read until exhaustion but is <strong>not</strong> closed by this method —
     * closing it remains the caller's responsibility.
     *
     * @param inputStream stream to hash
     * @param algorithm   {@link MessageDigest} algorithm name (e.g. {@code MD5}, {@code SHA-256}, {@code SHA-512})
     * @return hash as a lowercase hexadecimal string
     * @throws IOException              if reading the stream fails
     * @throws IllegalArgumentException if {@code algorithm} is not supported by the JVM
     */
    public String hash(InputStream inputStream, String algorithm) throws IOException {
        MessageDigest digest = this.digestFor(algorithm);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        return this.toHex(digest.digest());
    }

    /**
     * Computes the hash of the given file's content using the specified algorithm.
     *
     * @param file      file to hash
     * @param algorithm {@link MessageDigest} algorithm name (e.g. {@code MD5}, {@code SHA-256}, {@code SHA-512})
     * @return hash as a lowercase hexadecimal string
     * @throws IOException              if the file cannot be read
     * @throws IllegalArgumentException if {@code algorithm} is not supported by the JVM
     */
    public String hash(File file, String algorithm) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            return this.hash(fis, algorithm);
        }
    }

    /**
     * Computes the MD5 hash of the given bytes.
     *
     * @param data bytes to hash
     * @return hash as a lowercase hexadecimal string
     */
    public String md5(byte[] data) {
        return this.hash(data, "MD5");
    }

    /**
     * Computes the SHA-256 hash of the given bytes.
     *
     * @param data bytes to hash
     * @return hash as a lowercase hexadecimal string
     */
    public String sha256(byte[] data) {
        return this.hash(data, "SHA-256");
    }

    /**
     * Computes the SHA-512 hash of the given bytes.
     *
     * @param data bytes to hash
     * @return hash as a lowercase hexadecimal string
     */
    public String sha512(byte[] data) {
        return this.hash(data, "SHA-512");
    }

    /**
     * Computes the hash of {@code content} using {@code algorithm} and compares it, in constant
     * time, to {@code expectedHash} (case-insensitive hexadecimal).
     *
     * @param content      text whose hash is computed
     * @param expectedHash hexadecimal hash to compare against
     * @param algorithm    {@link MessageDigest} algorithm name (e.g. {@code MD5}, {@code SHA-256}, {@code SHA-512})
     * @return {@code true} if the computed hash matches {@code expectedHash}; {@code false} otherwise, including when {@code expectedHash} is null
     * @throws IllegalArgumentException if {@code algorithm} is not supported by the JVM
     */
    public boolean matches(String content, String expectedHash, String algorithm) {
        if (expectedHash == null) {
            return false;
        }

        String computed = this.hash(content, algorithm);

        return MessageDigest.isEqual(
                computed.getBytes(StandardCharsets.UTF_8),
                expectedHash.toLowerCase().getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Verifies an HMAC signature for the given payload — typically used to validate inbound
     * webhook requests. Comparison is done in constant time.
     *
     * @param payload   raw request body the signature was computed over
     * @param signature signature to verify, optionally prefixed (e.g. {@code "sha256="})
     * @param secret    shared secret used to compute the HMAC
     * @param algorithm {@link Mac} algorithm name (e.g. {@code HmacSHA256})
     * @return {@code true} if the signature is valid; {@code false} if {@code payload}, {@code signature}, or {@code secret} is null, or the signature does not match
     * @throws IllegalArgumentException if {@code algorithm} is not supported or {@code secret} is not a valid key for it
     */
    public boolean verifyHmac(String payload, String signature, String secret, String algorithm) {
        if (payload == null || signature == null || secret == null) {
            return false;
        }

        String providedHex = signature.contains("=")
                ? signature.substring(signature.indexOf('=') + 1)
                : signature;

        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), algorithm));
            String computedHex = this.toHex(mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));

            return MessageDigest.isEqual(
                    computedHex.getBytes(StandardCharsets.UTF_8),
                    providedHex.toLowerCase().getBytes(StandardCharsets.UTF_8)
            );
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new IllegalArgumentException("Algoritmo ou chave HMAC inválidos: " + algorithm, e);
        }
    }

    private MessageDigest digestFor(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Algoritmo de hash inválido: " + algorithm, e);
        }
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }

        return sb.toString();
    }
}
