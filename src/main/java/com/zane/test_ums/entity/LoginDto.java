package com.zane.test_ums.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class LoginDto implements Serializable {
    private String email;

    private String password;
}
