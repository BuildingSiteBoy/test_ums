package com.zane.test_ums.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.mapper.UserMapper;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Zanezeng
 */
@Component
public class Interceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(Interceptor.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserUtil userUtil;

    @Autowired
    TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        Long userId = userUtil.getUserId();

        // 判断访问者是否包含token
        if (token == null) {
            logger.info("该用户无token凭证");
            throw new MyException(ResultCode.INVALID_TOKEN, "用户凭证已失效（过期、登出）");
        } else {
            // DONE: 验证token是否正确
            try {
                return token.equals(tokenService.getToken(userId));
            } catch (Exception e) {
                throw new MyException(ResultCode.INVALID_TOKEN, "用户凭证已失效（过期、登出）");
            }
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
