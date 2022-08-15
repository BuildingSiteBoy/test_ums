package com.zane.test_ums.controller;

import com.zane.test_ums.dto.AccountDto;
import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultFactory;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.utils.CheckUtil;
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
     * @param account 注册信息(email, password)
     * @return 处理结果
     */
    @PostMapping("/register")
    public Result register(@RequestBody AccountDto account) {
        CheckUtil.checkLogin(account);
        return ResultFactory.buildSuccessResult(userService.register(account));
    }

    /**
     * 用户登录接口
     * @param account 登录信息(email, password)
     * @return 处理结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody AccountDto account) {
        CheckUtil.checkLogin(account);
        return ResultFactory.buildSuccessResult(userService.login(account));
    }

    /**
     * 用户登出接口
     * @return 处理结果
     */
    @PostMapping("/logout")
    public Result logout() {
        userService.logout();
        return ResultFactory.buildSuccessResult(null);
    }
}
