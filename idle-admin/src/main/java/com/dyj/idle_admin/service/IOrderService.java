package com.dyj.idle_admin.service;

import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.po.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dyj.idle_admin.domain.query.OrderPageQuery;
import com.dyj.idle_admin.domain.vo.OrderCountVO;
import com.dyj.idle_admin.domain.vo.OrderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
public interface IOrderService extends IService<Order> {

    ResultData<PageDTO<OrderVO>> getOrderPage(OrderPageQuery query);

    ResultData<List<OrderCountVO>> getOrderCount();

    ResultData<String> updateOrderStatus(Long orderId, Integer status) ;

}
