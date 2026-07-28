package com.dyj.idle.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDetail {
    private Long id;
    private Long goodsId;
    private Long buyer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creatTime;
    private Integer countDown;//倒计时时间
    private Integer state;
    private Integer useCoin;
    private double goodsPrice;
    private double goodsFreight;
    private double payMoney;
    private String goodsImage;
    private String goodsDesc;
    private String address;
    private String receiveName;
    private String receiveNumber;

}
