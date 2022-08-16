package com.zane.test_ums.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zane.test_ums.common.UserIdThreadLocal;
import com.zane.test_ums.common.exception.MyException;
import com.zane.test_ums.common.result.ResultCode;
import com.zane.test_ums.service.TokenService;
import com.zane.test_ums.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Zanezeng
 */
@Slf4j
@Component
public class AuthorInterceptor implements HandlerInterceptor {
    private final TokenService tokenService;
    @Autowired
    public AuthorInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 解析token，拿到userId，email，expireAt等
        // 2. 判断expireAt与当前时间比较，过期返回token过期
        // 3. 去比较userId的token list中是否包含该token，不包含反馈token失效
        // 4. userId、email等用户信息放入到threadLocal中
        String token = request.getHeader("Authorization");
        Long userId =  JwtUtil.decode(token);

        // Done: 验证token是否有效
        if (!JwtUtil.verify(token, userId)) {
            return false;
        }

        // DONE: 验证token是否正确
        if (tokenService.isRight(userId, token)) {
            if (null != userId) {
                log.debug("save user: {} to a new ThreadLocal!!!", userId);
                UserIdThreadLocal.set(userId);
            }
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
        log.debug("remove UserIdThreadLocal!");
        UserIdThreadLocal.remove();
    }
}
