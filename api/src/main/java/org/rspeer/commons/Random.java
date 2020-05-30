package org.rspeer.commons;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides functions to retrieve random numbers
 */
public class Random {

    public static int nextInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static int nextInt(int max) {
        return nextInt(0, max);
    }

    public static int nextInt(int min, int max) {
        return min == max ? min : ThreadLocalRandom.current().nextInt(min, max);
    }

    public static double nextDouble(double max) {
        return nextDouble(0, max);
    }

    public static double nextDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static boolean nextBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static <T> T nextElement(T[] elements) {
        return elements.length == 0 ? null : elements[nextInt(elements.length)];
    }

    /**
     * @return A random number from the given array, or -1 if the array is empty
     */
    public static int nextElement(int[] elements) {
        return elements.length == 0 ? -1 : elements[nextInt(elements.length)];
    }

    @SuppressWarnings("unchecked")
    public static <T> T nextElement(Collection<T> elements) {
        Object[] array = elements.toArray();
        return (T) nextElement(array);
    }

    public static double nextGaussian() {
        return ThreadLocalRandom.current().nextGaussian();
    }

    public static long nextLong(long max) {
        return nextLong(0, max);
    }

    public static long nextLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }
}
