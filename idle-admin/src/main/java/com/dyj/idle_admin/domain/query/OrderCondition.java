package com.dyj.idle_admin.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-20
 * @Description:
 * @Version: 1.0
 */
@Data
public class OrderCondition {
    //订单编号
    private Long id;
    //订单状态
    private Integer status;
    //买家姓名
    private String buyerName;
    /**
     * 时间区间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

}
