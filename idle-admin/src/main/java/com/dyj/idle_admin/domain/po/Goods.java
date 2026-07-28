package com.dyj.idle_admin.domain.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
 * @since 2025-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 卖家的id
     */
    private Long seller;

    /**
     * 商品描述
     */
    private String desc;

    /**
     * 商品价格
     */
    private Double price;

    /**
     * 商品的使用程度
     */
    private String usage;

    /**
     * 送货方式（包邮、不包邮、自取）
     */
    private String send;

    /**
     * 商品的创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private LocalDateTime creatTime;

    /**
     * 商品状态 0（编辑中）1（审核中）2（已经发布）3(拒绝发布）
     */
    private Integer state;

    /**
     * 商品的库存量
     */
    private Integer stock;

    /**
     * 运费
     */
    private Double freight;

    /**
     * 首图
     */
    private String firstPicture;

}
