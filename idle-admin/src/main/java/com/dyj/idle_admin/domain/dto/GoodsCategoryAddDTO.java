package com.dyj.idle_admin.domain.dto;

import lombok.Data;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-19
 * @Description:
 * @Version: 1.0
 */
@Data
public class GoodsCategoryAddDTO {
    /**
     * 分类名称
     */
    private String name;
    /**
     * 1 一级分类 2 二级分类 3 三级分类
     */
    private Integer type;
    /**
     * 父级id，可能为空
     */
    private Long faId;

}
