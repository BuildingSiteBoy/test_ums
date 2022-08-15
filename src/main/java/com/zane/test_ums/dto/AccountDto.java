package com.zane.test_ums.dto;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class AccountDto implements Serializable {

    @Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+[.]+[a-zA-Z0-9-.]+$", message = "-20101")
    private String email;

    @Pattern(regexp = "^[\\x21-\\x7e]{6,20}$", message = "-20102")
    private String password;
}
