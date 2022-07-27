package com.zane.test_ums.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

/**
 * 该工具类中有三个方法：
 * 1. verify()，参数：token，返回值：这个token是否存在
 * 2. encode(), 参数：签发人、存在时间和其他信息，返回值：token对应的String
 * 3. decode()，参数：token，返回值：荷载部分的键值对
 * @author Zanezeng
 */
@Component
public class JwtUtil {

    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param email 邮箱
     * @param password 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token,String email, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("email", email)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 加密过程，生成token签名,5min后过期
     * @param email 邮箱
     * @param password 用户的密码
     * @return 加密的token
     */
    public static String encode(String email, String password) {
        try {
            // 自定义过期时间：System.currentTimeMillis() + ttlMillis
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(password);
            // 附带email信息
            return JWT.create()
                    .withClaim("email", email)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解密过程：获得token中的信息无需password解密也能获得
     * @return token中包含的用户名
     */
    public static String decode(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("email").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}