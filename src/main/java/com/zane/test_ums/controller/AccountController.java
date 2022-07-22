package com.zane.test_ums.controller;

import com.zane.test_ums.entity.User;
import com.zane.test_ums.result.Result;
import com.zane.test_ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器：登录登出注册控制类
 * </p>
 *
 * @author Zanezeng
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/api/v1/user")
public class AccountController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result register() {
        return null;
    }

    /**
     * 用户登录接口
     * @param user 登录信息
     * @return 处理结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return null;
    }

    @PostMapping("/logout")
    public Result logout() {
        return null;
    }
}
