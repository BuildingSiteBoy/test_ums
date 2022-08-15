package com.zane.test_ums.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
@AllArgsConstructor
public class Result {
    /**
     * code: 响应状态码
     * message: 响应传递的信息
     * result: 响应传递的对象
     */
    private int code;
    private String message;
    private Object data;
}
