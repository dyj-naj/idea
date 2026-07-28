package com.dyj.idle_admin.domain.query;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-13
 * @Description:
 * @Version: 1.0
 */

@Data
public class UserCondition {
    private String nickname;
    private Integer sex;
    private String account;
    private String phone;
    private String mailbox;
    private Integer role;
    private Integer isForbidden;
}
