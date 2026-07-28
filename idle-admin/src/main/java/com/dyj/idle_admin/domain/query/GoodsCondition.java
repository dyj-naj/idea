package com.dyj.idle_admin.domain.query;

import com.dyj.idle_admin.enums.SortStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-18
 * @Description:
 * @Version: 1.0
 */
@Data
public class GoodsCondition {
    /**
     *价格区间
     */
    @JsonProperty("MinPrice")  // 显式指定JSON字段名
    private Double MinPrice;
    @JsonProperty("MaxPrice")
    private Double MaxPrice;

    /**
     * 时间区间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date MinTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date MaxTime;

    /**
     * 排序方式
     */
    private SortStatus sortStatus;

    /**
     * 运送方式
     */
    private String shippingWay;

    /**
     * 商品使用程度
     */
    private String usage;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 商品分类（三级），list[0]对应第一级，依次类推
     */
    private List<Long> categoryIdList;
}
