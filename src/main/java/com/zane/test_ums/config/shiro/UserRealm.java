package com.zane.test_ums.config.shiro;

import com.zane.test_ums.config.shiro.jwt.JwtToken;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 自定义的Realm
 * @author Zanezeng
 */
@Service
public class UserRealm extends AuthorizingRealm {
    private final UserService userService;

    @Autowired
    public UserRealm(UserService userService) {
        this.userService = userService;
    }

    /**
     * 重写supports()，用于标识这个Realm是专门用来验证JwtToken的，不负责其他的token
     * @param token jwtToken
     * @return true/false
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        // 此token为过滤器中传过来的JwtToken
        return token instanceof JwtToken;
    }

    /**
     * 授权：有当需要检测用户权限的时候才会调用此方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 验证：使用此方法进行用户名正确与否验证，错误抛出异常即可
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得email，用于与数据库进行对比
        String email = JwtUtil.decode(token);
        if (email == null){
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }

        User user = userService.getUserByEmail(email);
        if (user == null){
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }

        // TODO：过期时间的验证问题！！！
        if (!JwtUtil.verify(token, email, user.getPassword())) {
            throw new AuthenticationException("email or password error");
        }

        return new SimpleAuthenticationInfo(token, token, "userReam");
    }
}
