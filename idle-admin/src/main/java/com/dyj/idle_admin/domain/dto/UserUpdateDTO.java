package com.dyj.idle_admin.domain.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private Long userId;
    private String nickname;
    private String phone;
    private String mailbox;
    private Integer sex;
    private Integer role;
}
