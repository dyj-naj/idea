package com.dyj.idle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;
    private String account;
    private  String nickname;
    private String mailbox;
    private String password;
    private String headUrl;
    private String phone;
    private Integer sex;
    //规定时区和格式
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
    private String introduction;
    private Integer coinCnt;
    private Integer role;
    private Integer isForbidden;
}
