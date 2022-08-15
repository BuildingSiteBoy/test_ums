package com.zane.test_ums.common.result;

/**
 * @author Zanezeng
 */
public class ResultFactory {
    private ResultFactory() {
    }

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
     * @param resultCode ResultCode中定义的状态码和信息
     * @param data 传递的对象
     * @return result + data
     */
    public static Result buildResult(ResultCode resultCode, Object data) {
        return new Result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public static Result buildSuccessResult(Object data) {
        return buildResult(ResultCode.SUCCESS.getCode(), "success", data);
    }
}
