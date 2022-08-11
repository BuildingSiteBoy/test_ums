package com.zane.test_ums.controller;

import com.zane.test_ums.dto.LoginDto;
import com.zane.test_ums.result.Result;
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
        CheckUtil.checkLogin(register);
        return ResultFactory.buildProcessedResult(userService.register(register));
    }

    /**
     * 用户登录接口
     * @param login 登录信息(email, password)
     * @return 处理结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto login) {
        CheckUtil.checkLogin(login);
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
