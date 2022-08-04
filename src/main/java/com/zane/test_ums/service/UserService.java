package com.zane.test_ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zane.test_ums.dto.AlterDto;
import com.zane.test_ums.dto.LoginDto;
import com.zane.test_ums.dto.RegisterDto;
import com.zane.test_ums.dto.UserDto;
import com.zane.test_ums.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zanezeng
 * @since 2022-08-01
 */
public interface UserService extends IService<User> {
    /**
     * 注册用户
     * @param register：注册用户
     * @return userId + time
     */
     RegisterDto register(LoginDto register);

    /**
     * 用户登录
     * @param login：登录用户
     * @return userDto
     */
    UserDto login(LoginDto login);

    /**
     * 通过邮箱获取用户信息
     * @param email 邮箱
     * @return 用户
     */
    User getUserByEmail(String email);

    /**
     * 通过用户名获取密码
     * @param email: 邮箱
     * @return password
     */
    String getPassword(String email);

    /**
     * 更新用户信息
     * @param userId：用户id
     * @param userInfo：用户信息/*
     */
    void editUser(long userId, AlterDto userInfo);

    /**
     * 根据userId 修改用户密码
     * @param userId：用户id
     */
    void resetPassword(int userId);
}
