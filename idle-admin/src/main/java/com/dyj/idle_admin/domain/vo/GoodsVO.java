package com.dyj.idle_admin.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-18
 * @Description:
 * @Version: 1.0
 */

@Data
public class GoodsVO {
    private Long id;
    /**
     * 卖家名称
     */
    private String sellerAccount;

    /**
     * 商品描述
     */
    private String desc;

    /**
     * 卖家头像
     */
    private String sellerAvatar;

    /**
     * 商品图片
     */
    private String goodsPicture;


    private String goodsCategory;

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
    private Date createTime;

    /**
     * 商品状态 0（编辑中）1（审核中）2（已经发布）3(拒绝发布）
     */
    private Integer goodsStatus;

    /**
     * 运费
     */
    private Double freight;
}
