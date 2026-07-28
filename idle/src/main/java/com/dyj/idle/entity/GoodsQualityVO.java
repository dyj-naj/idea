package com.dyj.idle.entity;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-22
 * @Description:
 * @Version: 1.0
 */

@Data
public class GoodsQualityVO {
    private Long id;
    /**
     * 品质名称，比如全新、99新
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 建议折扣区间
     */
    private Integer minDiscount;
    private Integer maxDiscount;
}
