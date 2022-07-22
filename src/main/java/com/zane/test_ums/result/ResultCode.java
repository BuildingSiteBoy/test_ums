package com.zane.test_ums.result;

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
    SUCCESS(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    UNSUPPORTED_MEDIA_TYPE(415),
    SERVER_ERROR(500),
    /**
     * 0: 请求处理成功
     */
    PROCESSED_SUCCESS(0),
    /**
     * -10001: 服务器内部错误
     * -10002: 服务器繁忙
     * -10003: 服务器超时
     */
    SERVER_INTERNAL_ERROR(-10001),
    SERVER_BUSY(-10002),
    SERVER_TIME_OUT(-10003),
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
    ILLEGAL_EMAIL(-20101),
    ILLEGAL_PASSWORD(-20102),
    ILLEGAL_NICKNAME(-20103),
    ILLEGAL_ADDRESS(-20104),
    ILLEGAL_OLD_PASSWORD(-20105),
    ILLEGAL_NEW_PASSWORD(-20106),
    EXISTS_EMAIL(-20111),
    ERROR_PASSWORD(-20112),
    NON_EXISTS_EMAIL(-20113),
    /**
     * -20201: 用户凭证已失效（过期、登出）
     * -20202: 用户凭证错误，即传入的授权信息中该用户凭证合法但不属于该用户
     */
    INVALID_TOKEN(-20201),
    ERROR_TOKEN(-20202);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
