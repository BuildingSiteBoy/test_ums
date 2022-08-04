package com.zane.test_ums.controller;


import com.zane.test_ums.dto.AlterDto;
import com.zane.test_ums.dto.PasswordDto;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.result.ResultFactory;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.util.AesCipherUtil;
import com.zane.test_ums.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器：用户信息管理控制类
 * </p>
 *
 * @author Zanezeng
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/api/v1/user")
public class OldUserController {

    private final UserUtil userUtil;

    private final UserService userService;

    public OldUserController(UserUtil userUtil, UserService userService) {
        this.userUtil = userUtil;
        this.userService = userService;
    }

    @Autowired


    @PostMapping("/getUserInfo")
    public Result getUserInfo() {
        // 根据用户凭证获取id
//        long id = userUtil.getUserId();
        return ResultFactory.buildSuccessResult(userService.getById(1L));
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody AlterDto alterDto) {
        // TODO: 昵称合法性 + 地址合法性
        User user = userService.getById(userUtil.getUserId());
        user.setNickname(alterDto.getNickname());
        user.setAddress(alterDto.getAddress());
        userService.updateById(user);
        return ResultFactory.buildProcessedResult(null);
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody PasswordDto passwordDto) {
        String oldPassword = passwordDto.getOldPassword();
        String newPassword = passwordDto.getNewPassword();

        // TODO: 验证新老密码是否合法

        oldPassword = AesCipherUtil.encrypt(oldPassword);
        User user = userService.getById(userUtil.getUserId());
        if (oldPassword.equals(user.getPassword())) {
            newPassword = AesCipherUtil.encrypt(newPassword);
            user.setPassword(newPassword);
            userService.updateById(user);
            return ResultFactory.buildProcessedResult(null);
        } else {
            throw new MyException(ResultCode.ERROR_PASSWORD, "密码错误");
        }
    }
}
