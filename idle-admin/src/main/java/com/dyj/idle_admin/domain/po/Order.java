package com.dyj.idle_admin.domain.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("`order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 买家id
     */
    private Long buyer;

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
     * 送货方式
     */
    private String send;

    /**
     * 支付金额
     */
    private Double payMoney;

    /**
     * 支付方式
     */
    private String payMethods;

    /**
     * 收获地址
     */
    private String address;

    /**
     * 收货人昵称
     */
    private String receiveName;

    /**
     * 收货人电话
     */
    private String receiveNumber;


}
