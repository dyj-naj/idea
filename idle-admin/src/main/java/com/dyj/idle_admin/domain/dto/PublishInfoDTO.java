package com.dyj.idle_admin.domain.dto;

import com.dyj.idle_admin.domain.po.SystemInfo;
import lombok.Data;

import java.util.List;

/**
 * @Author: 杜英杰
 * @CreateTime: 2025-05-15
 * @Description:
 * @Version: 1.0
 */

@Data
public class PublishInfoDTO {
    //发送到对应用户还是广播送,(0为对应选择用户，1为广播)
    private Integer type;
    //如果type为0时，那么userIdList至少有一个数据
    private List<Long> userIdList;
    //消息内容
    private SystemInfo infoContent;
}
