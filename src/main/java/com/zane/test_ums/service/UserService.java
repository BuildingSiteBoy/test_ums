package com.zane.test_ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zane.test_ums.dto.*;
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
     * 用户登出
     */
    void logout();

    /**
     * 通过邮箱获取用户信息
     * @param email 邮箱
     * @return 用户
     */
    User getUserByEmail(String email);

    /**
     * 通过token获取当前用户信息
     * @return userInfoDto
     */
    UserInfoDto getUserInfoDto();

    /**
     * 通过用户名获取密码
     * @param email: 邮箱
     * @return password
     */
    String getPassword(String email);

    /**
     * 更新用户信息
     * @param userInfo：用户信息/*
     */
    void editUser(AlterDto userInfo);

    /**
     * 根据userId 修改用户密码
     * @param passwordDto：新老密码
     */
    void resetPassword(PasswordDto passwordDto);
}
