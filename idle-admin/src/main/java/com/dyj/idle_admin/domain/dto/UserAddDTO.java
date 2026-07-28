package com.dyj.idle_admin.domain.dto;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-13
 * @Description:
 * @Version: 1.0
 */

@Data
public class UserAddDTO {
    private String nickname;
    private String phone;
    private String mailbox;
    private Integer sex;
    private Integer role;
}
