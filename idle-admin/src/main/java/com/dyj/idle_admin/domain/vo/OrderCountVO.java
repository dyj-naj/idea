package com.dyj.idle_admin.domain.vo;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-20
 * @Description:
 * @Version: 1.0
 */

@Data
public class OrderCountVO {
    /**
     * 状态号
     */
    private Integer  status;
    /**
     * 数量
     */
    private Integer count;
}
