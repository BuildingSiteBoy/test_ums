package com.zane.test_ums.service;

/**
 * @author Zanezeng
 */
public interface TokenService {
    /**
     * 存储Token
     * @param userId: 用户id
     * @param token: 需要存储的token
     */
    void saveToken(Long userId, String token);

    /**
     * 获取Token
     * @param userId: 用户id
     * @return token
     */
    String getToken(Long userId);

    /**
     * 清空Token
     * @param userId: 用户id
     */
    void clearToken(Long userId);
}
