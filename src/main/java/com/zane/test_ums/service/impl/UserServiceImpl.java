package com.zane.test_ums.service.impl;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zane.test_ums.dto.*;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.utils.JwtUtil;
import com.zane.test_ums.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final UserUtil userUtil;
    private final TokenService tokenService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserUtil userUtil, TokenService tokenService) {
        this.userMapper = userMapper;
        this.userUtil = userUtil;
        this.tokenService = tokenService;
    }

    @Override
    public RegisterDto register(AccountDto account) {
        if (getUserByEmail(account.getEmail()) != null) {
            throw new MyException(ResultCode.EXISTS_EMAIL, "邮箱已存在！！！");
        }

        User user = new User();
        user.setEmail(account.getEmail());
        user.setPassword(BCrypt.hashpw(account.getPassword(), BCrypt.gensalt()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userMapper.insert(user);

        user = getUserByEmail(account.getEmail());

        return new RegisterDto(user.getId(), user.getCreateTime());
    }

    @Override
    public UserDto login(AccountDto account) {
        User user = getUserByEmail(account.getEmail());
        if (user == null) {
            throw new MyException(ResultCode.NON_EXISTS_EMAIL, "邮箱不存在！！！");
        }

        if (!BCrypt.checkpw(account.getPassword(), user.getPassword())) {
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
    public void editUser(AlterDto userInfo) {
        User user = getById(userUtil.getUserId());
        user.setNickname(userInfo.getNickname());
        user.setAddress(userInfo.getAddress());
        user.setUpdateTime(LocalDateTime.now());
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
            user.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(user);
        } else {
            throw new MyException(ResultCode.ERROR_PASSWORD, "老密码不正确！！！");
        }
    }
}
