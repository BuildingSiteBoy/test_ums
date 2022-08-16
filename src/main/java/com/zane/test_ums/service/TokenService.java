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
     * 判断Token是否正确
     * @param userId: 用户ID
     * @param token: token
     * @return token
     */
    boolean isRight(Long userId, String token);

    /**
     * 清空Token
     * @param userId: 用户id
     */
    void clearToken(Long userId);
}
