package com.zane.test_ums.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class UserInfoDto implements Serializable {

    private Long userId;

    private String email;

    private String nickname;

    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz", timezone = "GMT+8")
    private LocalDateTime createAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz", timezone = "GMT+8")
    private LocalDateTime updateAt;
}
