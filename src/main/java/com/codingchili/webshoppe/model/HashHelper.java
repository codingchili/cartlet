package com.codingchili.webshoppe.model;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by Robin on 2015-09-28.
 * <p>
 * Handles the hashing of passwords and the generation
 * of the salts used in the hashing.
 * <p>
 * reference: https://crackstation.net/hashing-security.htm#javasourcecode
 */

public class HashHelper {
    private final static int ITERATIONS = 8;
    private final static int MEMORY = 2048;
    private static final int PARALLELISM = 2;
    private final static int HASH_BYTES = 48;
    private final static int SALT_BYTES = 24;
    private static Argon2 argon2;

    static {
        argon2 = Argon2Factory.create(SALT_BYTES, HASH_BYTES);
    }

    /**
     * @param password the password to generate a hash of.
     * @return a hashed password in base64.
     */
    public static String hash(String password) {
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password);
    }

    /**
     * Verifies the given password hash against a plaintext password.
     * @param password a hashed password.
     * @param plaintext a plaintext password to hash and verify.
     * @return true if the given passwords matches.
     */
    public static boolean verify(String password, String plaintext) {
        return argon2.verify(password, plaintext);
    }

    /**
     * Compares two byte arrays in constant time.
     *
     * @param first  the first array.
     * @param second the second array.
     * @return true if both arrays are of equal size and content.
     */
    public static boolean equals(byte[] first, byte[] second) {
        int result = 0;

        if (first.length != second.length)
            return false;

        for (int i = 0; i < first.length; i++)
            result |= (first[i] ^ second[i]);

        return result == 0;
    }
}
