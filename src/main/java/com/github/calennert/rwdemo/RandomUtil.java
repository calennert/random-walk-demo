package com.github.calennert.rwdemo;

import java.security.SecureRandom;

/**
 * Provides convenience methods for obtaining random values.
 *
 * @author calennert (Chris Lennert)
 */
public final class RandomUtil {

    private static final SecureRandom secureRandom = new SecureRandom();

    public static boolean randomBoolean() {
        return secureRandom.nextBoolean();
    }

    public static float randomFloat() {
        return secureRandom.nextFloat();
    }

    private RandomUtil() {
        // not instantiable
    }
}
