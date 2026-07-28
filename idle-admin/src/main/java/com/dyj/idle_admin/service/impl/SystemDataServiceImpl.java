package com.dyj.idle_admin.service.impl;

import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.po.SystemData;
import com.dyj.idle_admin.mapper.SystemDataMapper;
import com.dyj.idle_admin.service.ISystemDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyj
 * @since 2025-05-12
 */
@Service
public class SystemDataServiceImpl extends ServiceImpl<SystemDataMapper, SystemData> implements ISystemDataService {

    @Override
    public ResultData<SystemData> getSystemData() {
        SystemData data = baseMapper.selectOne(null);
        if(data!=null){
            return ResultData.Success(data);
        }else{
            return ResultData.Error("无法正确获取数据");
        }
    }
}
