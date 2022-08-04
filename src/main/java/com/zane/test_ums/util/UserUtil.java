package com.zane.test_ums.util;

import com.zane.test_ums.entity.User;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zanezeng
 */
@Component
public class UserUtil {
    private final UserService userService;

    @Autowired
    public UserUtil(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取当前登录用户Token
     * @return token
     */
    public String getToken() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 解密获取当前用户email
     * @return email
     */
    public String getEmail() {
        return JwtUtil.decode(getToken());
    }

    /**
     * 获取当前用户
     * @return User
     */
    public User getUser(){
        User user = userService.getUserByEmail(getEmail());
        // 判断用户是否存在
        if (user == null) {
            throw new MyException(ResultCode.NON_EXISTS_EMAIL, "该邮箱不存在");
        }
        return user;
    }

    /**
     * 获取当前登录用户的ID
     * @return id
     */
    public Long getUserId() {
        return getUser().getId();
    }
}
