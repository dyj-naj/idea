package com.dyj.idle_admin.mapper;

import com.dyj.idle_admin.domain.po.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyj.idle_admin.domain.vo.OrderCountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Select("SELECT \n" +
            "    state AS status,\n" +
            "    COUNT(*) AS `count`\n" +
            "FROM \n" +
            "    `order`\n" +
            "GROUP BY \n" +
            "    state;")
    List<OrderCountVO> getOrderCount();
}
