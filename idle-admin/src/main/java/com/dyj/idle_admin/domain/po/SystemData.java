package com.dyj.idle_admin.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dyj
 * @since 2025-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_data")
public class SystemData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 访问量
     */
    private Integer visits;

    /**
     * 成交额
     */
    private double turnover;

    /**
     * 成交量
     */
    private Integer volume;

    /**
     * 反馈数
     */
    private Integer feedbacks;


}
