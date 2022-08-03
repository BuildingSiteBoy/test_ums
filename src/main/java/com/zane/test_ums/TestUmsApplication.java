package com.zane.test_ums;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Zanezeng
 */
@MapperScan("com.zane.test_ums.mapper")
@SpringBootApplication
public class TestUmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestUmsApplication.class, args);
    }

}
