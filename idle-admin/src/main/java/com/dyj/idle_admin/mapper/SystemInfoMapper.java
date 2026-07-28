package com.dyj.idle_admin.mapper;

import com.dyj.idle_admin.domain.po.SystemInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dyj
 * @since 2025-05-15
 */
@Mapper
public interface SystemInfoMapper extends BaseMapper<SystemInfo> {
    @Insert("insert into system_info_user(si_id,target_id) values(#{si_id},#{target_id})")
    int insertUserSystemInfo(Long si_id,Long target_id);

    @Insert("INSERT INTO system_info_user(si_id, target_id) " +
            "SELECT #{si_id}, id FROM user")
    int insertBroadcastSystemInfo(@Param("si_id") Long si_id);

}
