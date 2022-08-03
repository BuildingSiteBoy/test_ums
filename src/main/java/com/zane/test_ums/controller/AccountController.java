package com.zane.test_ums.controller;

import com.zane.test_ums.dto.LoginDto;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.result.ResultFactory;
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
    public Result register(@RequestBody LoginDto register) {
        // TODO: 邮箱合法性验证 + 密码合法性验证

        User user = userService.getUserByEmail(register.getEmail());
        if (user != null) {
            return ResultFactory.buildResult(ResultCode.EXISTS_EMAIL, "邮箱已存在", null);
        }

        return ResultFactory.buildProcessedResult(userService.register(register));
    }

    /**
     * 用户登录接口
     * @param login 登录信息
     * @return 处理结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto login) {
        // TODO: 邮箱合法性验证 + 密码合法性验证

        User user = userService.getUserByEmail(login.getEmail());
        if (user == null) {
            return ResultFactory.buildResult(ResultCode.NON_EXISTS_EMAIL, "邮箱不存在", null);
        }
        return ResultFactory.buildProcessedResult(userService.login(login));
    }

    @PostMapping("/logout")
    public Result logout() {
        return null;
    }
}
