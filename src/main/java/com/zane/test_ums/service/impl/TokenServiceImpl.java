package com.zane.test_ums.service.impl;

import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Zanezeng
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public void saveToken(Long userId, String token) {
        String key = userId.toString();
        redisUtil.set(key, token);
    }

    @Override
    public String getToken(Long userId) {
        return redisUtil.get(userId.toString()).toString();
    }

    @Override
    public void clearToken(Long userId) {
        redisUtil.del(userId.toString());
    }
}
