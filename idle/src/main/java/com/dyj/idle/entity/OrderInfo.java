package com.dyj.idle.entity;

import lombok.Data;

@Data
public class OrderInfo {
    private Long id;
    private String goodsImage;
    private String goodsDesc;
    private double resPay;
}
