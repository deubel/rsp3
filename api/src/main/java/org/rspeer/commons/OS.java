package org.rspeer.commons;

public enum OS {

    WINDOWS,
    MAC,
    LINUX,
    UNKNOWN;

    public static String getHomeDirectory() {
        return System.getProperty("user.home");
    }

    public static OS get() {
        String os = System.getProperty("os.name");
        for (OS o : OS.values()) {
            if (os.contains(o.toString())) {
                return o;
            }
        }

        return UNKNOWN;
    }

    @Override
    public String toString() {
        String orig = super.toString();
        return Character.toUpperCase(orig.charAt(0)) + orig.substring(1).toLowerCase();
    }

}
