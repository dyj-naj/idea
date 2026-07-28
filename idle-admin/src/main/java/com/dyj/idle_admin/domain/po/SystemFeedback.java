package com.dyj.idle_admin.domain.po;

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
@TableName("system_feedback")
public class SystemFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

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
