package com.zane.test_ums.service.impl;

import com.zane.test_ums.entity.User;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zanezeng
 * @since 2022-07-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
