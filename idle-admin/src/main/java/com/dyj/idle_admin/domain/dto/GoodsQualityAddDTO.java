package com.dyj.idle_admin.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-19
 * @Description:
 * @Version: 1.0
 */

@Data
public class GoodsQualityAddDTO {
    private Long id;
    private String name;
    private String description;
    private Integer minDiscount;
    private Integer maxDiscount;
}
