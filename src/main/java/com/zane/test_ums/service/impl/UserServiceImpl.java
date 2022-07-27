package com.zane.test_ums.service.impl;

import com.zane.test_ums.entity.AlterDto;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
