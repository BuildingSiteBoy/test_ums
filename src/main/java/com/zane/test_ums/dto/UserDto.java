package com.zane.test_ums.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class UserDto implements Serializable {
    private String token;

    private Integer expiresIn;

    private Long userId;

    private String email;

    private String nickname;

    private String address;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
