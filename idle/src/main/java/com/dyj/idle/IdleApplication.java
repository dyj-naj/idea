package com.dyj.idle;


import com.dyj.idle.mapper.ChatMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IdleApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdleApplication.class, args);
    }
}
