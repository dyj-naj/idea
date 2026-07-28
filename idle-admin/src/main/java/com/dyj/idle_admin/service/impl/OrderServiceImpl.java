package com.dyj.idle_admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dyj.idle_admin.common.PageDTO;
import com.dyj.idle_admin.common.ResultData;
import com.dyj.idle_admin.domain.po.Goods;
import com.dyj.idle_admin.domain.po.Order;
import com.dyj.idle_admin.domain.query.OrderCondition;
import com.dyj.idle_admin.domain.query.OrderPageQuery;
import com.dyj.idle_admin.domain.vo.OrderCountVO;
import com.dyj.idle_admin.domain.vo.OrderVO;
import com.dyj.idle_admin.mapper.GoodsMapper;
import com.dyj.idle_admin.mapper.OrderMapper;
import com.dyj.idle_admin.service.IGoodsService;
import com.dyj.idle_admin.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyj.idle_admin.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dyj
 * @since 2025-05-20
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final GoodsMapper goodsMapper;

    @Override
    public ResultData<PageDTO<OrderVO>> getOrderPage(OrderPageQuery query) {
        Page<Order> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper queryWrapper=new QueryWrapper();
        OrderCondition condition = query.getCondition();
        if(condition!=null){
            if(condition.getId()!=null){
                queryWrapper.like("id", condition.getId());
            }
            if(condition.getStatus()!=null){
                queryWrapper.eq("state", condition.getStatus());
            }
            if(condition.getBuyerName()!=null){
                queryWrapper.like("receive_name", condition.getBuyerName());
            }
            if(condition.getStartTime()!=null&&condition.getEndTime()!=null){
                queryWrapper.between("creat_time", condition.getStartTime(), condition.getEndTime());
            }
        }
        Page<Order> orderPage = this.page(page, queryWrapper);
        if(orderPage.getRecords()==null){
            return ResultData.Success(PageDTO.empty(orderPage));
        }
        List<Order> orderList= orderPage.getRecords();

        Map<Long, Long> map = orderList.stream().collect(Collectors.toMap(Order::getId, Order::getGoodsId));

        List<OrderVO> orderVOS = BeanUtils.copyList(orderList, OrderVO.class);

        //封装VO
        for(OrderVO orderVO:orderVOS){
            Long goodsId = map.get(orderVO.getId());
            String firstPicture = goodsMapper.getFirstPicture(goodsId);
            String desc = goodsMapper.getDesc(goodsId);
            orderVO.setGoodsPicture(firstPicture);
            orderVO.setGoodsDesc(desc);
        }

        return ResultData.Success(PageDTO.of(page,orderVOS));
    }

    @Override
    public ResultData<List<OrderCountVO>> getOrderCount() {
        List<OrderCountVO> orderCount = baseMapper.getOrderCount();
        return ResultData.Success(orderCount);
    }

    @Override
    public ResultData<String> updateOrderStatus(Long orderId, Integer status) {
        boolean b = this.lambdaUpdate().set(Order::getState, status).eq(Order::getId, orderId).update();
        if(b){
            return ResultData.Success("修改成功");
        }else{
            return ResultData.Error("修改失败");
        }
    }
}
