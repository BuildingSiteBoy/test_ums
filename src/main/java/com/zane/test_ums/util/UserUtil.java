package com.zane.test_ums.util;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zanezeng
 */
@Component
public class UserUtil {
    private final UserMapper userMapper;

    private final HttpServletRequest request;

    @Autowired
    public UserUtil(UserMapper userMapper, HttpServletRequest request) {
        this.userMapper = userMapper;
        this.request = request;
    }

    public String getToken() {
        return request.getHeader("Authorization");
    }

    /**
     * 解密获取当前用户email
     * @return email
     */
    public String getEmail() {
        return JwtUtil.decode(getToken());
    }

    /**
     * 获取当前用户
     * @return User
     */
    public User getUser(){
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("email", getEmail())
        );
        // 判断用户是否存在
        if (user == null) {
            throw new MyException(ResultCode.NON_EXISTS_EMAIL, "该邮箱不存在");
        }
        return user;
    }

    /**
     * 获取当前登录用户的ID
     * @return id
     */
    public Long getUserId() {
        return getUser().getId();
    }
}
