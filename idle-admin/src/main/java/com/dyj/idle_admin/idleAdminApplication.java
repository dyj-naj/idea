package com.dyj.idle_admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-07
 * @Description:
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("com.dyj.idle_admin.mapper")
public class idleAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(idleAdminApplication.class, args);
    }
}
