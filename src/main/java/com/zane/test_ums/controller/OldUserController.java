package com.zane.test_ums.controller;


import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultFactory;
import com.zane.test_ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    @Autowired
    UserService userService;

    @PostMapping("/getUserInfo")
    public Result getUserInfo() {

        // 根据用户凭证获取id
        int id = 0;
//        return ResultFactory.buildSuccessResult(userService.getById())
        return ResultFactory.buildSuccessResult(userService.getById(id));
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo() {
        return null;
    }

    @PostMapping("/updatePassword")
    public Result updatePassword() {
//        userService.
        return null;
    }
}