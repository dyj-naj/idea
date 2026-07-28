package com.dyj.idle_admin.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
 * @since 2025-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_info")
public class SystemInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 通知id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 通知类型（type=0：修改小猪币的通知 type=......）
     */
    private Integer type;

    /**
     * 发布消息用户的id
     */
    private Long publishId;

    /**
     * 小猪币修改数量，当此条消息类型为0，changeNum不为空，如果是减少那么这个就为负数，否则为正数
     */
    private Integer changeNum;

    /**
     * 通知内容，当消息类型为0是，就是小猪币修改的原因
     */
    private String content;

    /**
     * 通知时间，前端不需要处理，后端来处理
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 过期时间，一般是通知的30天后，前端不需要处理，后端来处理
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private LocalDateTime expirationTime;

}
