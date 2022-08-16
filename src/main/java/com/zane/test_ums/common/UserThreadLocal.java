package com.zane.test_ums.common;

import com.zane.test_ums.entity.User;

/**
 * @author Zanezeng
 */
public class UserThreadLocal {

    private UserThreadLocal() {
    }

    private static final ThreadLocal<User> USER_THREAD = new ThreadLocal<>();

    public static User get() {
        return USER_THREAD.get();
    }

    public static void set(User user) {

        USER_THREAD.set(user);
    }

    public static void remove() {
        USER_THREAD.remove();
    }
}
