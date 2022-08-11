package com.zane.test_ums.service.impl;

import com.zane.test_ums.entity.User;
import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.util.JwtUtil;
import com.zane.test_ums.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zanezeng
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String PREFIX_REDIS_USER = "zane_user:";
    private static final String PREFIX_REDIS_TOKEN = "zane_token:";
    private static final long REDIS_EXPIRE_TIME = 1800;
    private static final int LIST_LENGTH = 5;

    private final RedisUtil redisUtil;

    @Autowired
    public TokenServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void saveToken(Long userId, String token) {
        String key = PREFIX_REDIS_USER + userId.toString();
        String tokenKey = PREFIX_REDIS_TOKEN + token;
        if (redisUtil.hasKey(key) && redisUtil.lGetListSize(key) == LIST_LENGTH) {
            redisUtil.rPop(key);
        }
        redisUtil.lSet(key, token, REDIS_EXPIRE_TIME);
        redisUtil.hSet(tokenKey, "ttl", REDIS_EXPIRE_TIME, REDIS_EXPIRE_TIME);
        redisUtil.hSet(tokenKey, "userId", userId, REDIS_EXPIRE_TIME);
    }

    @Override
    public boolean isRight(User user, String token) {
        String key = PREFIX_REDIS_USER + user.getId().toString();

        if (JwtUtil.verify(token, user.getEmail(), user.getPassword())) {
            long len = redisUtil.lGetListSize(key);
            for (int i = 0; i < len; i++) {
                if (token.equals(redisUtil.lGetIndex(key, i))) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void clearToken(Long userId) {
        String key = PREFIX_REDIS_USER + userId;
        long len = redisUtil.lGetListSize(key);
        for (int i = 0; i < len ; i++) {
            String tokenKey = PREFIX_REDIS_TOKEN + redisUtil.lGetIndex(key, i);
            redisUtil.del(tokenKey);
            redisUtil.rPop(key);
        }
        redisUtil.del(key);
    }
}
