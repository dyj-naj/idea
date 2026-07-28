package com.dyj.idle_admin.domain.vo;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-13
 * @Description:
 * @Version: 1.0
 */

@Data
public class UserInfoVO {
    private Long id;
    /**
     * 账号
     */
    private String account;
    /**
     * 昵称
     */
    private  String nickname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String mailbox;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 拥有的小猪币的数量
     */
    private Integer coinCnt;
    /**
     * 用户角色
     */
    private Integer role;

    /**
     * 状态
     */
    private Integer isForbidden;

}
