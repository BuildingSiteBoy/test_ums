package com.zane.test_ums.common.result;

/**
 * @author Zanezeng
 */
public enum ResultCode {
    /**
     * 200: 请求成功
     * 400: 请求错误
     * 401: 请求授权失败
     * 404: 未找到路径/资源
     * 415: 请求未指定合适的Content Type
     * 500: 服务器产生内部错误
     */
    SUCCESS(200, "请求成功"),
    BAD_REQUEST(400, "请求错误"),
    UNAUTHORIZED(401, "请求授权失败"),
    NOT_FOUND(404, "未找到路径/资源"),
    UNSUPPORTED_MEDIA_TYPE(415, "请求未指定合适的Content Type"),
    SERVER_ERROR(500, "服务器产生内部错误"),
    /**
     * 0: 请求处理成功
     */
    PROCESSED_SUCCESS(0, "success"),
    /**
     * -10001: 服务器内部错误
     * -10002: 服务器繁忙
     * -10003: 服务器超时
     */
    SERVER_INTERNAL_ERROR(-10001, "服务器内部错误"),
    SERVER_BUSY(-10002, "服务器繁忙"),
    SERVER_TIME_OUT(-10003, "服务器超时"),
    /**
     * -20101: 邮箱不合法
     * -20102: 密码不合法
     * -20103: 昵称不合法
     * -20104: 地址不合法
     * -20105: 老密码不合法
     * -20106: 新密码不合法
     * -20111: 邮箱已注册
     * -20112: 密码/老密码不正确
     * -20113: 邮箱不存在
     */
    ILLEGAL_EMAIL(-20101, "邮箱不合法"),
    ILLEGAL_PASSWORD(-20102, "密码不合法"),
    ILLEGAL_NICKNAME(-20103, "昵称不合法"),
    ILLEGAL_ADDRESS(-20104, "地址不合法"),
    ILLEGAL_OLD_PASSWORD(-20105, "老密码不合法"),
    ILLEGAL_NEW_PASSWORD(-20106, "新密码不合法"),
    EXISTS_EMAIL(-20111, "邮箱已注册"),
    ERROR_PASSWORD(-20112, "密码/老密码不正确"),
    NON_EXISTS_EMAIL(-20113, "邮箱不存在"),
    /**
     * -20201: 用户凭证已失效（过期、登出）
     * -20202: 用户凭证已失效（过期、登出）
     */
    INVALID_TOKEN(-20201, "用户凭证已失效（过期、登出）"),
    ERROR_TOKEN(-20202, "用户凭证错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsgByCode(int code) {
        for (ResultCode value : values()) {
            if (value.getCode() == code) {
                return value.getMsg();
            }
        }

        return "error";
    }
}
