package com.zane.test_ums.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class RegisterDto implements Serializable {
    private Long userId;
    private LocalDateTime createAt;
}
