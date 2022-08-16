package com.zane.test_ums.common;

/**
 * @author Zanezeng
 */
public class UserIdThreadLocal {

    private UserIdThreadLocal() {
    }

    private static final ThreadLocal<Long> USER_THREAD = new ThreadLocal<>();

    public static Long get() {
        return USER_THREAD.get();
    }

    public static void set(Long userId) {

        USER_THREAD.set(userId);
    }

    public static void remove() {
        USER_THREAD.remove();
    }
}
