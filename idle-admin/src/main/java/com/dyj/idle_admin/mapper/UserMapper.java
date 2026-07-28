package com.dyj.idle_admin.mapper;

import com.dyj.idle_admin.domain.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dyj
 * @since 2025-05-09
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where account=#{account} and password = #{password}")
    User Login(@Param("account") String account,@Param("password") String password);

}
