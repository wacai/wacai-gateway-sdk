package com.wacai.gateway.sdk.common;

/**
 * 
 * @author bairen
 *
 */
public abstract class Assert {

    protected Assert() {
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }

        if (obj instanceof String && ((String) obj).isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object obj, RuntimeException exeception) {
        if (obj == null) {
            throw exeception;
        }
        if (obj instanceof String && ((String) obj).isEmpty()) {
            throw exeception;
        }
    }

}