package com.zane.test_ums.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zanezeng
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto implements Serializable {

    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssz", timezone = "GMT+8")
    private LocalDateTime createAt;
}
