package com.zane.test_ums.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zane.test_ums.entity.User;
import com.zane.test_ums.exception.MyException;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Zanezeng
 */
@Component
public class Interceptor implements HandlerInterceptor {

    private final UserUtil userUtil;

    private final TokenService tokenService;

    @Autowired
    public Interceptor(UserUtil userUtil, TokenService tokenService) {
        this.userUtil = userUtil;
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        User user = userUtil.getUser();

        // DONE: 验证token是否正确
        if (tokenService.isRight(user, token)) {
            return true;
        } else {
            throw new MyException(ResultCode.INVALID_TOKEN, "用户凭证已失效（过期、登出）");
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
