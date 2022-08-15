package com.zane.test_ums.dto;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class PasswordDto implements Serializable {

    @Pattern(regexp = "^[\\x21-\\x7e]{6,20}$", message = "-20105")
    private String oldPassword;

    @Pattern(regexp = "^[\\x21-\\x7e]{6,20}$", message = "-20106")
    private String newPassword;
}
