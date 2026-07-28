package com.dyj.idle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ChatWindow {
    private Long id;//这个聊天窗口的id
    private Long seller;//卖家id
    private Long buyer;//买家id
    private Long goodsId;//商品id
    private String sellerHead;//卖家头像
    private String buyerHead;//买家头像
    private String sellerAccount;//卖家账号
    private String buyerAccount;//买家账号
    private String goodsImage;//商品图片
    private double goodsPrice;//商品价格
    private String goodsSend;//商品运输方式
    private double goodsFreight;//商品运费
    private Integer goodsStock;//商品库存
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date lastTime;//最后聊天时间
}
