package org.rspeer.commons;

/**
 * Utilities for the manipulation of strings
 */
public class StringCommons {

    public static String sanitize(String text) {
        return replaceJagspace(replaceColorTag(text));
    }

    public static String replaceJagspace(String text) {
        if (text == null) {
            return null;
        }
        return text.replace('\u00A0', ' ');
    }

    public static String replaceColorTag(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("(<col=[0-9a-f]+>|</col>)", "");
    }
}
