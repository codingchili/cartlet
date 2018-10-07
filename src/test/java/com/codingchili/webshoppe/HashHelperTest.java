package com.codingchili.webshoppe;

import com.codingchili.webshoppe.model.HashHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Robin Duda
 *
 * Tests for the password hashing.
 */
public class HashHelperTest {
    private static final String PASS = "myPassword123";
    private static final String OTHER = "other";
    private static final int MIN_HASH_MS = 5;

    @Test
    public void generateAndVerify() {
        String hash = HashHelper.hash(PASS);
        Assert.assertTrue(HashHelper.verify(hash, PASS));
    }

    @Test
    public void generateAndNotVerify() {
        String hash = HashHelper.hash(PASS);
        Assert.assertFalse(HashHelper.verify(hash, OTHER));
    }

    @Test
    public void testTimeTaken() {
        for (int i = 0; i < 5; i++) {
            HashHelper.hash(PASS + i);
        }

        long then = System.currentTimeMillis();
        HashHelper.hash(PASS);
        long ms = (System.currentTimeMillis() - then);

        // this test will fail in a few years, and that's the point.
        if (ms < MIN_HASH_MS) {
            Assert.fail("time taken to hash password too low, was: " + ms + "ms. expected >" + MIN_HASH_MS + "ms.");
        }
    }
}
