package com.zane.test_ums.controller;


import java.time.LocalDateTime;

import com.zane.test_ums.dto.AlterDto;
import com.zane.test_ums.dto.PasswordDto;
import com.zane.test_ums.dto.RegisterDto;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.result.ResultFactory;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.utils.CheckUtil;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/getUserInfo")
    public Result getUserInfo() {
        // 根据用户凭证获取id
        return ResultFactory.buildSuccessResult(userService.getUserInfoDto());
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody AlterDto alterDto) {
        String nickname = alterDto.getNickname();
        String address = alterDto.getAddress();

        // DONE: 昵称合法性 + 地址合法性
        if (!CheckUtil.checkNickname(nickname)) {
            throw new MyException(ResultCode.ILLEGAL_NICKNAME, "昵称不合法！！！");
        }
        if (!CheckUtil.checkAddress(address)) {
            throw new MyException(ResultCode.ILLEGAL_ADDRESS, "地址不合法！！！");
        }

        userService.editUser(alterDto);
        return ResultFactory.buildSuccessResult(null);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody PasswordDto passwordDto) {
        String oldPassword = passwordDto.getOldPassword();
        String newPassword = passwordDto.getNewPassword();

        // DONE: 验证新老密码是否合法
        if (!CheckUtil.checkPassword(oldPassword)) {
            throw new MyException(ResultCode.ILLEGAL_OLD_PASSWORD, "老密码不合法！！！");
        }
        if (!CheckUtil.checkPassword(newPassword)) {
            throw new MyException(ResultCode.ILLEGAL_NEW_PASSWORD, "新密码不合法！！！");
        }

        userService.resetPassword(passwordDto);
        userService.logout();
        return ResultFactory.buildSuccessResult(null);
    }

    @PostMapping("/hello")
    public Result getHello() {
        RegisterDto result = new RegisterDto();
        result.setUserId(5L);
        result.setCreateAt(LocalDateTime.now());
        return ResultFactory.buildSuccessResult(result);
    }
}
