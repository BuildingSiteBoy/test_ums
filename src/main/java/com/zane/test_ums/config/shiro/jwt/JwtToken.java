package com.zane.test_ums.config.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JWTToken: Shiro 用户名和密码的载体
 * @author Zanezeng
 */
public class JwtToken implements AuthenticationToken {
    /**
     * token凭证
     */
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
