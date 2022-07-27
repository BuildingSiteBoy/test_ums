package com.zane.test_ums.result;

/**
 * @author Zanezeng
 */
public class ResultFactory {
    /**
     * 返回处理结果
     * @param resultCode 状态码
     * @param msg 传递的消息
     * @param data 传递的对象
     * @return code + msg + data
     */
    public static Result buildResult(int resultCode, String msg, Object data) {
        return new Result(resultCode, msg, data);
    }

    /**
     * 返回处理结果
     * @param resultCode ResultCode中定义的状态码
     * @param msg 传递的消息
     * @param data 传递的对象
     * @return code + msg + data
     */
    public static Result buildResult(ResultCode resultCode, String msg, Object data) {
        return new Result(resultCode.code, msg, data);
    }

    public static Result buildSuccessResult(Object data) {
        return buildResult(ResultCode.SUCCESS, "请求成功", data);
    }

    public static Result buildProcessedResult(Object data) {
        return buildResult(ResultCode.PROCESSED_SUCCESS, "success", data);
    }

    public static Result buildFailResult(String msg) {
        return buildResult(ResultCode.BAD_REQUEST, msg, null);
    }
}
