package com.zane.test_ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zane.test_ums.entity.AlterDto;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zanezeng
 * @since 2022-07-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int register(User user) {
        return 0;
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectOne(
                new QueryWrapper<User>().eq("email", email)
        );
    }

    @Override
    public String getPassword(String username) {
        return null;
    }

    @Override
    public void editUser(int userId, AlterDto userInfo) {
        User user = getById(userId);
        user.setNickname(userInfo.getNickname());
        user.setAddress(userInfo.getAddress());
        userMapper.updateById(user);
    }

    @Override
    public void resetPassword(int userId) {

    }
}
