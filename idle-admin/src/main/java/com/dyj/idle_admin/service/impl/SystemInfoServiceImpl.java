package com.dyj.idle_admin.service.impl;

import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.dto.PublishInfoDTO;
import com.dyj.idle_admin.domain.po.SystemInfo;
import com.dyj.idle_admin.domain.po.User;
import com.dyj.idle_admin.mapper.SystemInfoMapper;
import com.dyj.idle_admin.service.ISystemInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyj.idle_admin.service.IUserService;
import com.dyj.idle_admin.utils.IdWorker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyj
 * @since 2025-05-15
 */
@Service
@RequiredArgsConstructor
public class SystemInfoServiceImpl extends ServiceImpl<SystemInfoMapper, SystemInfo> implements ISystemInfoService {

    private final IUserService userService;

    @Override
    public ResultData<String> sendCoinChangeInfo(PublishInfoDTO dto) {
        System.out.println(dto);
        try{
            //先保存系统信息
            SystemInfo info = dto.getInfoContent();
            if(info.getId()==null){//代表着是一条新通知
                IdWorker idWorker=new IdWorker(0,0);
                info.setId(idWorker.nextId());
                info.setCreateTime(LocalDateTime.now());
                info.setExpirationTime(LocalDateTime.now().plusDays(30));
                this.save(dto.getInfoContent());
            }
            //查看发送类型
            if(dto.getType()==0){
                //则需要依次想list里面的用户发送信息
                for(Long userId:dto.getUserIdList()){
                    this.baseMapper.insertUserSystemInfo(info.getId(),userId);
                    if(dto.getInfoContent().getType()==0){//需要修改小猪币
                        userService.lambdaUpdate()
                                .eq(User::getId, userId)
                                .setSql("coin_cnt = CASE WHEN (coin_cnt + {0}) < 0 THEN 0 ELSE coin_cnt + {0} END", info.getChangeNum()) // {0} 对应第一个参数
                                .update();
                    }
                }
            }else{
                //向全部用户发送信息
                this.baseMapper.insertBroadcastSystemInfo(info.getId());
                if(dto.getInfoContent().getType()==0){//需要修改小猪币
                    userService.lambdaUpdate()
                            .setSql("coin_cnt = CASE WHEN (coin_cnt + {0}) < 0 THEN 0 ELSE coin_cnt + {0} END", info.getChangeNum()) // {0} 对应第一个参数
                            .update();
                }
            }
            return ResultData.Success("消息发布成功");
        }catch (Exception e){
            return ResultData.Error("消息发布错误");
        }
    }
}
