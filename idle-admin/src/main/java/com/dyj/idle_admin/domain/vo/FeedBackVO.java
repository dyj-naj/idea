package com.dyj.idle_admin.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class FeedBackVO {
    /**
     * 反馈主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 发布人id
     */
    private Long publisherId;

    /**
     * 发布人账号
     */
    private String publisherAccount;

    /**
     * 发布人头像
     */
    private String publisherAvatar;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈评价
     */
    private Integer star;

    /**
     * 反馈状态（0未回复。1已回复）
     */
    private Integer status;

    /**
     * 提交时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date submitTime;

    /**
     * 回复内容
     */
    private String responseContent;

    /**
     * 回复时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date responseTime;
}
