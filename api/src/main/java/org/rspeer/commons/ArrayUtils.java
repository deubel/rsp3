package org.rspeer.commons;

import java.util.function.Function;

/**
 * Provides utilities for arrays
 */
public class ArrayUtils {

    public static <T> boolean contains(T[] array, T value) {
        for (T elem : array) {
            if (elem.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static <T, M> M[] map(T[] array, Function<T, M> mapper) {
        @SuppressWarnings("unchecked")
        M[] mapped = (M[]) new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            mapped[i] = mapper.apply(array[i]);
        }

        return mapped;
    }

    public static Integer[] mapPrimitive(int[] array) {
        Integer[] mapped = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            mapped[i] = array[i];
        }
        return mapped;
    }

    public static boolean contains(int[] array, int value) {
        for (int elem : array) {
            if (elem == value) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsExact(String[] array, String value) {
        for (String elem : array) {
            if (elem.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsExactInsensitive(String[] array, String value) {
        for (String elem : array) {
            if (elem.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsPartialInsensitive(String[] array, String value) {
        for (String elem : array) {
            if (value.toLowerCase().contains(elem.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}