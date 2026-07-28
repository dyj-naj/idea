package com.dyj.idle_admin.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;

import java.util.Date;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-20
 * @Description:
 * @Version: 1.0
 */

@Data
public class FeedBackCondition {
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 评分数
     */
    private Integer starNum;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 时间区间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

}
