package com.dyj.idle.entity;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-22
 * @Description:
 * @Version: 1.0
 */

@Data
public class GoodsTransportVO {
    private Long id;
    /**
     * 如韵达快递之类的
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 所属公司
     */
    private String company;
    /**
     * 预计送达时间
     */
    private String estimatedDays;
    /**
     * 基础运费
     */
    private Integer freight;
}
