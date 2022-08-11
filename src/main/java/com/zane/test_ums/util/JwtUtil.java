package com.zane.test_ums.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 该工具类中有三个方法：
 * 1. verify()，参数：token，返回值：这个token是否存在
 * 2. encode(), 参数：签发人、存在时间和其他信息，返回值：token对应的String
 * 3. decode()，参数：token，返回值：荷载部分的键值对
 * @author Zanezeng
 */
@Component
@Slf4j
public class JwtUtil {

    /**
     * 过期时间5分钟
     */
    public static final long EXPIRE_TIME = 30 * 60 * 1000L;
    public static final String CLAIM = "email";

    private JwtUtil() {
    }

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
                    .withClaim(CLAIM, email)
                    .build();
            verifier.verify(token);
            return true;
        } catch (RuntimeException e) {
            log.error("JWTToken认证解密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new MyException(ResultCode.ERROR_TOKEN,
                    "用户凭证错: JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
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
                    .withClaim(CLAIM, email)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("错误信息JWTToken加密出现UnsupportedEncodingException异常:{}", e.getMessage());
            throw new MyException(ResultCode.SERVER_ERROR,
                    "错误信息JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    /**
     * 解密过程：获得token中的信息无需password解密也能获得
     * @return token中包含的邮箱
     */
    public static String decode(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CLAIM).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:{}", e.getMessage());
            throw new MyException(ResultCode.ERROR_TOKEN,
                    "用户凭证错误：解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }
}