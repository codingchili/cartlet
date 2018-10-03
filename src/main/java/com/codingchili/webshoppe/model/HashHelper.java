package com.codingchili.webshoppe.model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by Robin on 2015-09-28.
 *
 * Handles the hashing of passwords and the generation
 * of the salts used in the hashing.
 *
 * reference: https://crackstation.net/hashing-security.htm#javasourcecode
 */

class HashHelper {
    private final static int ITERATIONS = 65535;
    private final static int KEY_BITS = 1024;
    private final static int SALT_BYTES = 64;
    private final static String ALGORITHM = "PBKDF2WithHmacSHA1";

    public static String hash(String password, String salt) {
        try {
            PBEKeySpec key = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_BITS);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = keyFactory.generateSecret(key).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Hashing Error: NoSuchCipher");
        }
    }

    public static String generateSalt() {
        byte[] salt = new byte[SALT_BYTES];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
