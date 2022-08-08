package com.zane.test_ums.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class UserDto extends UserInfoDto implements Serializable {
    private String token;

    private Integer expiresIn;
}
