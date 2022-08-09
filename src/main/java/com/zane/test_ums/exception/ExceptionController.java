package com.zane.test_ums.exception;

import javax.servlet.http.HttpServletRequest;

import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 * @author Zanezeng
 */
@RestControllerAdvice
public class ExceptionController {

    /**
     * 捕捉其他多有自定义的异常
     * @param e 自定义异常
     * @return result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyException.class)
    public Result handle(MyException e) {
        return ResultFactory.buildResult(e.getResultCode(), e.getMessage(), null);
    }

    /**
     * 捕捉其他所有异常
     * @param request 请求
     * @param ex 异常
     * @return result
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result globalException(HttpServletRequest request, Throwable ex) {
        return ResultFactory.buildResult(getStatus(request).value(), ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}
