package com.zane.test_ums.controller;


import com.zane.test_ums.dto.RegisterDto;
import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultFactory;
import com.zane.test_ums.util.DateTimeUtil;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/hello")
    public Result getUserInfo() {
        RegisterDto result = new RegisterDto();
        result.setUserId(1L);
        result.setCreateAt(DateTimeUtil.getUtc());
        return ResultFactory.buildSuccessResult(result);
    }
}
