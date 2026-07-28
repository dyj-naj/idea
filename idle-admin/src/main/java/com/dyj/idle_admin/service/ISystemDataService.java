package com.dyj.idle_admin.service;

import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.po.SystemData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dyj
 * @since 2025-05-12
 */
public interface ISystemDataService extends IService<SystemData> {

    ResultData<SystemData> getSystemData();
}
