package com.zane.test_ums.service;

import com.zane.test_ums.entity.AlterDto;
import com.zane.test_ums.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Zanezeng
 * @since 2022-07-26
 */
public interface UserService extends IService<User> {
    /**
     * 注册用户
     * @param user：注册用户
     * @return 0:用户名或密码为空；1：注册成功；2：用户已存在
     */
    int register(User user);

    /**
     * 通过邮箱获取用户信息
     * @param email 邮箱
     * @return 用户
     */
    User getUserByEmail(String email);

    /**
     * 通过用户名获取密码
     * @param username：用户名
     * @return password
     */
    String getPassword(String username);

    /**
     * 根据userId 更改用户信息
     * @param userId：用户id
     */
    void editUser(int userId, AlterDto userInfo);

    /**
     * 根据userId 修改用户密码
     * @param userId：用户id
     */
    void resetPassword(int userId);
}
