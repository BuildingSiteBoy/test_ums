package com.zane.test_ums.controller;

import com.zane.test_ums.dto.LoginDto;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.result.ResultFactory;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.util.CheckUtil;
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
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户注册接口
     * @param register 注册信息(email, password)
     * @return 处理结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody LoginDto register) {
        // DONE: 邮箱合法性验证 + 密码合法性验证
        if (!CheckUtil.checkEmail(register.getEmail())) {
            throw new MyException(ResultCode.ILLEGAL_EMAIL, "邮箱不合法！！！");
        }
        if (!CheckUtil.checkPassword(register.getPassword())) {
            throw new MyException(ResultCode.ILLEGAL_PASSWORD, "密码不合法！！！");
        }

        return ResultFactory.buildProcessedResult(userService.register(register));
    }

    /**
     * 用户登录接口
     * @param login 登录信息(email, password)
     * @return 处理结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto login) {
        // DONE: 邮箱合法性验证 + 密码合法性验证
        if (!CheckUtil.checkEmail(login.getEmail())) {
            throw new MyException(ResultCode.ILLEGAL_EMAIL, "邮箱不合法！！！");
        }
        if (!CheckUtil.checkPassword(login.getPassword())) {
            throw new MyException(ResultCode.ILLEGAL_PASSWORD, "密码不合法！！！");
        }

        return ResultFactory.buildProcessedResult(userService.login(login));
    }

    /**
     * 用户登出接口
     * @return 处理结果
     */
    @PostMapping("/logout")
    public Result logout() {
        userService.logout();
        return ResultFactory.buildProcessedResult(null);
    }
}
