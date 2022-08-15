package com.zane.test_ums.controller;


import com.zane.test_ums.dto.AlterDto;
import com.zane.test_ums.dto.PasswordDto;
import com.zane.test_ums.common.result.Result;
import com.zane.test_ums.common.result.ResultFactory;
import com.zane.test_ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Zanezeng
 * @since 2022-08-01
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 更新用户信息
     * @return result
     */
    @PostMapping("/getUserInfo")
    public Result getUserInfo() {
        return ResultFactory.buildSuccessResult(userService.getUserInfoDto());
    }

    /**
     * 更新用户信息
     * @param alterDto 要修改的信息
     * @return result
     */
    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody @Validated AlterDto alterDto) {
        userService.editUser(alterDto);
        return ResultFactory.buildSuccessResult(null);
    }

    /**
     * 修改用户密码
     * @param passwordDto 新老密码
     * @return result
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody @Validated PasswordDto passwordDto) {
        userService.resetPassword(passwordDto);
        userService.logout();
        return ResultFactory.buildSuccessResult(null);
    }
}
