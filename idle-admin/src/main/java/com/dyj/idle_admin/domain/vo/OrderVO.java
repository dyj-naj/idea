package com.dyj.idle_admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-20
 * @Description:
 * @Version: 1.0
 */
@Data
public class OrderVO {
    /**
     * 订单编号
     */
    private Long id;

    /**
     * 商品图片
     */
    private String goodsPicture;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 使用的小猪币 (100小猪币=1元)
     */
    private Integer useCoin;

    /**
     * 商品价格
     */
    private Double goodsPrice;

    /**
     * 商品运费
     */
    private Double goodsFreight;

    /**
     * 订单创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creatTime;

    /**
     * 支付时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date payTime;

    /**
     * 支付状态 0待付款 1已支付 2 已取消 已收货  4 已收货 5已完成 6退款中 7 已退款
     */
    private Integer state;

    /**
     * 支付方式
     */
    private String payMethods;

    /**
     * 收获地址
     */
    private String address;

    /**
     * 送货方式
     */
    private String send;


    /**
     * 支付金额
     */
    private Double payMoney;

    /**
     * 买家昵称
     */
    private String receiveName;

    /**
     * 买家电话
     */
    private String receiveNumber;


}
