package com.dyj.idle_admin.domain.dto;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-20
 * @Description:
 * @Version: 1.0
 */
@Data
public class GoodsTransportAddDTO {

    /**
     * 更新时，id不为空，添加时，id为空，数据库自增
     */
    private Long id;

    private String name;

    private String company;

    /**
     * 预计运输时间
     */
    private String estimatedDays;
    /**
     * 运输方式描述
     */
    private String description;

    /**
     * 是否启用
     */
    private Boolean isActive;

    /**
     * 基础运费
     */
    private Integer freight;
}
