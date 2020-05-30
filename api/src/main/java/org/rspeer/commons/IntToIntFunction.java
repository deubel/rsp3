package org.rspeer.commons;

import java.util.function.Function;

/**
 * Represents a function that produces an int-valued result.  This is the
 * {@code int}-producing and consuming primitive specialization for {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(int)}.
 *
 * @see Function
 */
@FunctionalInterface
public interface IntToIntFunction {
    int apply(int value);
}
