package com.dyj.idle.entity;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsInfo {
    private Long goodsId;
    private String send;
    private String goodsDescription;
    private double goodsPrice;
    private Integer state;
    private String goodsImage;
    private String userAccount;
    private String userAvatar;
    private Integer lookNum;//商品的浏览量
    private Integer wantNum;//商品的想要量
}
