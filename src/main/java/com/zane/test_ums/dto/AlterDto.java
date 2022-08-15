package com.zane.test_ums.dto;

import java.io.Serializable;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * @author Zanezeng
 */
@Data
public class AlterDto implements Serializable {

    @Size(max = 32, message = "-20103")
    private String nickname;

    @Size(max = 255, message = "-20104")
    private String address;
}
