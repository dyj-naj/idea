package com.dyj.idle.entity;

import lombok.Data;

import java.util.List;

@Data
public class SubCategory {
    private Long id;
    private String label;
    private List<SubCategory> children;
}
