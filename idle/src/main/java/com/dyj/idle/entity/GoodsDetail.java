package com.dyj.idle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GoodsDetail {
    private Long goodsId;
    private Long seller;
    private String sellerHead;//销售者的头像
    private String sellerAccount;//销售者账号
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date creatTime;//商品发布时间
    private String goodsDesc;
    private double goodsPrice;
    private String goodsUsage;
    private Integer goodsStock;
    private double goodsFreight;//商品运费
    private String goodsSend;
    private Integer lookNum;
    private Integer wantNum;
    private List<String> imageUrl;
    private boolean isCollect;

}
