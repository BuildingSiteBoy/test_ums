package com.zane.test_ums.exception;

import com.zane.test_ums.result.ResultCode;

/**
 * 自定义异常类
 * @author Zanezeng
 */
public class MyException extends RuntimeException {
    private final ResultCode resultCode;

    public MyException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

}
