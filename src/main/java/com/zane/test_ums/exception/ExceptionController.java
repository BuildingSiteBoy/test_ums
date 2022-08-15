package com.zane.test_ums.exception;

import javax.servlet.http.HttpServletRequest;

import com.zane.test_ums.result.Result;
import com.zane.test_ums.result.ResultCode;
import com.zane.test_ums.result.ResultFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 * @author Zanezeng
 */
@Slf4j
@RestControllerAdvice
public class ExceptionController {

    /**
     * 捕捉自定义的异常
     * @param e 自定义异常
     * @return result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyException.class)
    public Result handle(MyException e) {
        int errorCode = e.getResultCode().getCode();
        String msg = e.getResultCode().getMsg() + ": " + e.getMessage();
        log.info("errorCode: {}, message: {}", errorCode, msg);
        return ResultFactory.buildResult(errorCode, msg, null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleFormatError(MethodArgumentNotValidException e) {
        String exceptionMsg = e.getBindingResult().getFieldError().getDefaultMessage();
        if (exceptionMsg != null) {
            int errorCode = Integer.parseInt(exceptionMsg);
            String msg = ResultCode.getMsgByCode(errorCode);
            log.info("errorCode: {}, message: {}", errorCode, msg);
            return ResultFactory.buildResult(errorCode, msg, null);
        }
        return ResultFactory.buildResult(500, "服务器产生内部错误", null);
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
