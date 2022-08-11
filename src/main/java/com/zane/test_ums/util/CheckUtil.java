package com.zane.test_ums.util;

import com.zane.test_ums.dto.LoginDto;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.result.ResultCode;
import org.springframework.stereotype.Component;

/**
 * @author Zanezeng
 */
@Component
public class CheckUtil {

    private CheckUtil() {
    }

    /**
     * REGX_EMAIL: 验证邮箱的正则表达式
     */
    private static final String REGX_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.]+[a-zA-Z0-9-.]+$";

    /**
     * REGX_PASSWORD: 验证密码的正则表达式
     */
    private static final String REGX_PASSWORD = "^[a-zA-Z0-9!#$%&'*+,-.\\\\/:;<=>?@^_`~|\"\\(\\)\\[\\]\\{\\}]{6,20}$";

    /**
     * 验证邮箱是否合法
     * @param email 需验证的邮箱
     * @return boolean
     */
    public static boolean checkEmail(String email) {
        return !email.matches(REGX_EMAIL);
    }

    /**
     * 验证密码是否合法
     * @param password 需验证的密码
     * @return boolean
     */
    public static boolean checkPassword(String password) {
        return !password.matches(REGX_PASSWORD);
    }

    /**
     * 验证登录时邮箱和密码的合法性
     * @param login 需验证的信息
     */
    public static void checkLogin(LoginDto login) {
        // DONE: 邮箱合法性验证 + 密码合法性验证
        if (CheckUtil.checkEmail(login.getEmail())) {
            throw new MyException(ResultCode.ILLEGAL_EMAIL, "邮箱不合法！！！");
        }
        if (CheckUtil.checkPassword(login.getPassword())) {
            throw new MyException(ResultCode.ILLEGAL_PASSWORD, "密码不合法！！！");
        }
    }

    /**
     * 验证昵称的合法性
     * @param nickname 需验证的昵称
     * @return boolean
     */
    public static boolean checkNickname(String nickname) {
        return nickname.length() < 33;
    }

    /**
     * 验证地址的合法性
     * @param address 需验证的地址
     * @return boolean
     */
    public static boolean checkAddress(String address) {
        return address.length() < 256;
    }


}
