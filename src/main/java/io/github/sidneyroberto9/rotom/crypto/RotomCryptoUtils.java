package io.github.sidneyroberto9.rotom.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for symmetric encryption of secrets at rest, using AES/GCM with a random IV
 * embedded in the ciphertext.
 */
public class RotomCryptoUtils {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;

    private final SecureRandom random = new SecureRandom();

    /**
     * Encrypts {@code plaintext} with AES/GCM using the given key. The result is Base64-encoded
     * and contains the random IV followed by the ciphertext, so it can be passed directly to
     * {@link #decrypt(String, String)}.
     *
     * @param plaintext text to encrypt
     * @param base64Key AES key (16, 24, or 32 bytes), Base64-encoded
     * @return Base64-encoded IV + ciphertext
     * @throws IllegalArgumentException if the key is invalid or encryption fails
     */
    public String encrypt(String plaintext, String base64Key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(base64Key), "AES");

            byte[] iv = new byte[GCM_IV_LENGTH];
            this.random.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));

            byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            byte[] combined = new byte[GCM_IV_LENGTH + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, GCM_IV_LENGTH);
            System.arraycopy(encrypted, 0, combined, GCM_IV_LENGTH, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Falha ao criptografar dados: chave ou algoritmo inválido", e);
        }
    }

    /**
     * Decrypts a value produced by {@link #encrypt(String, String)}.
     *
     * @param base64Ciphertext Base64-encoded IV + ciphertext, as returned by {@link #encrypt(String, String)}
     * @param base64Key        AES key used to encrypt, Base64-encoded
     * @return decrypted plaintext
     * @throws IllegalArgumentException if the key or ciphertext is invalid, or decryption fails
     */
    public String decrypt(String base64Ciphertext, String base64Key) {
        try {
            byte[] combined = Base64.getDecoder().decode(base64Ciphertext);

            byte[] iv = new byte[GCM_IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, GCM_IV_LENGTH);

            byte[] encrypted = new byte[combined.length - GCM_IV_LENGTH];
            System.arraycopy(combined, GCM_IV_LENGTH, encrypted, 0, encrypted.length);

            SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(base64Key), "AES");

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));

            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Falha ao decriptografar dados: chave ou texto cifrado inválido", e);
        }
    }
}
