package com.zane.test_ums.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zanezeng
 * @since 2022-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zane_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * userId, 唯一值
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 邮箱，匹配该正则"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$"
     */
    private String email;

    /**
     * 密码,6～20位，允许字符范围为 ASCII 表编号33到126的字符
     */
    private String password;

    /**
     * 用户昵称，支持 emoji，最大 32 个字符
     */
    private String nickname;

    /**
     * 用户地址，最大长度 255 个字符
     */
    private String address;

    /**
     * 版本信息
     */
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT+8")
    private LocalDateTime updateTime;


}
