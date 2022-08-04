package com.zane.test_ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zane.test_ums.dto.AlterDto;
import com.zane.test_ums.dto.LoginDto;
import com.zane.test_ums.dto.RegisterDto;
import com.zane.test_ums.dto.UserDto;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.util.AesCipherUtil;
import com.zane.test_ums.util.DateTimeUtil;
import com.zane.test_ums.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zanezeng
 * @since 2022-08-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public RegisterDto register(LoginDto register) {
        if (getUserByEmail(register.getEmail()) != null) {
            throw new MyException(ResultCode.EXISTS_EMAIL, "邮箱已存在！！！");
        }

        User user = new User();
        user.setEmail(register.getEmail());

        String encodePassword = AesCipherUtil.encrypt(register.getPassword());
        user.setPassword(encodePassword);

        user.setCreateTime(DateTimeUtil.getUtc());
        user.setUpdateTime(DateTimeUtil.getUtc());

        userMapper.insert(user);

        user = getUserByEmail(register.getEmail());
        System.out.println(user);

        return new RegisterDto(user.getId(), user.getCreateTime());
    }

    @Override
    public UserDto login(LoginDto login) {
        User user = getUserByEmail(login.getEmail());
        if (user == null) {
            throw new MyException(ResultCode.NON_EXISTS_EMAIL, "邮箱不存在！！！");
        }

        String encodePassword = AesCipherUtil.encrypt(login.getPassword());
        if (!encodePassword.equals(user.getPassword())) {
            throw new MyException(ResultCode.ERROR_PASSWORD, "密码不正确！！！");
        }

        String token = JwtUtil.encode(user.getEmail(), user.getPassword());
//        httpServletResponse.setHeader("Authorization", token);
//        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");

        UserDto myUser = new UserDto();
        myUser.setToken(token);
        myUser.setExpiresIn((int) JwtUtil.EXPIRE_TIME / 1000);
        myUser.setUserId(user.getId());
        myUser.setEmail(user.getEmail());
        myUser.setNickname(user.getNickname());
        myUser.setAddress(user.getAddress());
        myUser.setCreateAt(user.getCreateTime());
        myUser.setUpdateAt(user.getUpdateTime());

        return myUser;
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectOne(
                new QueryWrapper<User>().eq("email", email)
        );
    }

    @Override
    public String getPassword(String email) {
        return getUserByEmail(email).getPassword();
    }

    @Override
    public void editUser(long userId, AlterDto userInfo) {
        User user = getById(userId);
        user.setNickname(userInfo.getNickname());
        user.setAddress(userInfo.getAddress());
//        user.setUpdateTime();
        userMapper.updateById(user);
    }

    @Override
    public void resetPassword(int userId) {

    }
}
