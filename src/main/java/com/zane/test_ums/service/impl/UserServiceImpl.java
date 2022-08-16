package com.zane.test_ums.service.impl;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zane.test_ums.common.UserIdThreadLocal;
import com.zane.test_ums.common.exception.MyException;
import com.zane.test_ums.common.result.ResultCode;
import com.zane.test_ums.dto.*;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
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
    private final TokenService tokenService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, TokenService tokenService) {
        this.userMapper = userMapper;
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
            throw new MyException(ResultCode.NON_EXISTS_EMAIL, "！！！");
        }

        if (!BCrypt.checkpw(account.getPassword(), user.getPassword())) {
            throw new MyException(ResultCode.ERROR_PASSWORD, "！！！");
        }

        String token = JwtUtil.encode(user.getId());
        tokenService.saveToken(user.getId(), token);

        UserDto myUser = new UserDto();

        // 参数拷贝
        BeanUtils.copyProperties(user, myUser);

        myUser.setToken(token);
        myUser.setExpiresIn((int) JwtUtil.EXPIRE_TIME / 1000);

        return myUser;
    }

    @Override
    public void logout() {
        // DONE: 清空用户凭证
        Long userId = UserIdThreadLocal.get();
        log.info("User {}: clear Token!", userId);
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
        User user = getById(UserIdThreadLocal.get());

        // 参数拷贝
        BeanUtils.copyProperties(user, userInfoDto);

        return userInfoDto;
    }

    @Override
    public void editUser(AlterDto userInfo) {
        User user = getById(UserIdThreadLocal.get());

        // 参数拷贝
        BeanUtils.copyProperties(userInfo, user);

        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    @Override
    public void resetPassword(PasswordDto passwordDto) {
        String oldPassword = passwordDto.getOldPassword();
        String newPassword = passwordDto.getNewPassword();

        User user = getById(UserIdThreadLocal.get());
        if (BCrypt.checkpw(oldPassword, user.getPassword())) {
            newPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPassword(newPassword);
            user.setUpdateTime(LocalDateTime.now());
            userMapper.updateById(user);
        } else {
            throw new MyException(ResultCode.ERROR_PASSWORD, "请重试！！！");
        }
    }
}
