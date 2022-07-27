package com.zane.test_ums.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zanezeng
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * userId, 唯一值
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 邮箱，匹配该正则"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$"
     */
    private String email;

    /**
     * 密码,6～20位，允许字符范围为 ASCII 表编号33到126的字符
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
