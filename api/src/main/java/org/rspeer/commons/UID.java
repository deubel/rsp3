package org.rspeer.commons;

import java.util.concurrent.atomic.AtomicLong;

public class UID {

    private static final AtomicLong MRU = new AtomicLong(0);

    public static long generate() {
        return MRU.getAndIncrement();
    }
}
