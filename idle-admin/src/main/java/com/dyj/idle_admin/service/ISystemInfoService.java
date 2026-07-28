package com.dyj.idle_admin.service;

import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.PublishInfoDTO;
import com.dyj.idle_admin.domain.po.SystemInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dyj
 * @since 2025-05-15
 */
public interface ISystemInfoService extends IService<SystemInfo> {

    ResultData<String> sendCoinChangeInfo(PublishInfoDTO dto);
}
