package com.zane.test_ums.exception;

import com.zane.test_ums.result.ResultCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义异常类
 * @author Zanezeng
 */
@Slf4j
public class MyException extends RuntimeException {
    private final ResultCode resultCode;

    public MyException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
        String logMsg = resultCode.toString() + ": " + msg;
        log.info(logMsg);
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

}
