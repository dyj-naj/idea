package com.dyj.idle.entity;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-22
 * @Description:
 * @Version: 1.0
 */

@Data
public class FeedbackDTO {
    private Long userId;
    private String userAccount;
    private String content;
    private Integer star;
}
