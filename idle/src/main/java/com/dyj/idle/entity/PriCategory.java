package com.dyj.idle.entity;

import lombok.Data;

import java.util.List;

@Data
public class PriCategory {
    private Long id;
    private String label;//一级分类标签
    private List<SubCategory> children;//它对应的二级分类标签
    private String imageUrl;//图表路径
}
