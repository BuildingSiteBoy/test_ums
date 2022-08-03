package com.zane.test_ums.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class AlterDto implements Serializable {
    private String nickname;

    private String address;
}
