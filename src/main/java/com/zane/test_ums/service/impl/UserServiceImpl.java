package com.zane.test_ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zane.test_ums.dto.*;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.util.DateTimeUtil;
import com.zane.test_ums.util.JwtUtil;
import com.zane.test_ums.util.UserUtil;
import org.mindrot.jbcrypt.BCrypt;
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

    @Autowired
    UserUtil userUtil;

    @Autowired
    TokenService tokenService;

    @Override
    public RegisterDto register(LoginDto register) {
        if (getUserByEmail(register.getEmail()) != null) {
            throw new MyException(ResultCode.EXISTS_EMAIL, "邮箱已存在！！！");
        }

        User user = new User();
        user.setEmail(register.getEmail());

        String encodePassword = BCrypt.hashpw(register.getPassword(), BCrypt.gensalt());
        user.setPassword(encodePassword);

        user.setCreateTime(DateTimeUtil.getUtc());
        user.setUpdateTime(DateTimeUtil.getUtc());

        userMapper.insert(user);

        user = getUserByEmail(register.getEmail());

        return new RegisterDto(user.getId(), user.getCreateTime());
    }

    @Override
    public UserDto login(LoginDto login) {
        User user = getUserByEmail(login.getEmail());
        if (user == null) {
            throw new MyException(ResultCode.NON_EXISTS_EMAIL, "邮箱不存在！！！");
        }

        if (!BCrypt.checkpw(login.getPassword(), user.getPassword())) {
            throw new MyException(ResultCode.ERROR_PASSWORD, "密码不正确！！！");
        }

        String token = JwtUtil.encode(user.getEmail(), user.getPassword());
        tokenService.saveToken(user.getId(), token);

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
    public void logout() {
        // DONE: 清空用户凭证
        Long userId = userUtil.getUserId();
        tokenService.clearToken(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectOne(
                new QueryWrapper<User>().eq("email", email)
        );
    }

    @Override
    public UserInfoDto getUserInfoDto() {
        UserInfoDto userInfoDto = new UserInfoDto();
        User user = userMapper.selectById(userUtil.getUserId());
        userInfoDto.setUserId(user.getId());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setNickname(user.getNickname());
        userInfoDto.setAddress(user.getAddress());
        userInfoDto.setCreateAt(user.getCreateTime());
        userInfoDto.setUpdateAt(user.getUpdateTime());
        return userInfoDto;
    }

    @Override
    public String getPassword(String email) {
        return getUserByEmail(email).getPassword();
    }

    @Override
    public void editUser(AlterDto userInfo) {
        User user = getById(userUtil.getUserId());
        user.setNickname(userInfo.getNickname());
        user.setAddress(userInfo.getAddress());
        user.setUpdateTime(DateTimeUtil.getUtc());
        userMapper.updateById(user);
    }

    @Override
    public void resetPassword(PasswordDto passwordDto) {
        String oldPassword = passwordDto.getOldPassword();
        String newPassword = passwordDto.getNewPassword();

        User user = getById(userUtil.getUserId());
        if (BCrypt.checkpw(oldPassword, user.getPassword())) {
            newPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPassword(newPassword);
            user.setUpdateTime(DateTimeUtil.getUtc());
            userMapper.updateById(user);
        } else {
            throw new MyException(ResultCode.ERROR_PASSWORD, "老密码不正确！！！");
        }
    }
}
