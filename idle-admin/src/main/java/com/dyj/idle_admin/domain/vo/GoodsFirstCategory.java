package com.dyj.idle_admin.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-19
 * @Description: 一级分类
 * @Version: 1.0
 */
@Data
public class GoodsFirstCategory {
    private Long id;
    private String name;
    private List<GoodsSecondCategory> children;
}
