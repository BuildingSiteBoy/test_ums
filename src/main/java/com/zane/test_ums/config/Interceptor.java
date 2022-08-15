package com.zane.test_ums.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zane.test_ums.common.exception.MyException;
import com.zane.test_ums.common.result.ResultCode;
import com.zane.test_ums.entity.User;
import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.service.UserService;
import com.zane.test_ums.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Zanezeng
 */
@Component
public class Interceptor implements HandlerInterceptor {
    private final UserService userService;
    private final TokenService tokenService;
    @Autowired
    public Interceptor(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        User user = userService.getUserByToken();

        // Done: 验证token是否有效
        if (!JwtUtil.verify(token, user.getId())) {
            return false;
        }

        // DONE: 验证token是否正确
        if (tokenService.isRight(user, token)) {
            return true;
        } else {
            throw new MyException(ResultCode.INVALID_TOKEN, "！！！");
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
