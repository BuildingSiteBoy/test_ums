package com.zane.test_ums.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class PasswordDto implements Serializable {

    private String oldPassword;

    private String newPassword;
}
