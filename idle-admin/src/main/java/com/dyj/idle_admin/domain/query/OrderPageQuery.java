package com.dyj.idle_admin.domain.query;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-20
 * @Description:
 * @Version: 1.0
 */
@Data
public class OrderPageQuery {
    private Integer pageNum;
    private Integer pageSize;
    private OrderCondition condition;

}
