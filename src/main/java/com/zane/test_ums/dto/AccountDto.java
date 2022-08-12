package com.zane.test_ums.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class AccountDto implements Serializable {

    private String email;

    private String password;
}
