package com.dyj.idle.service;

import com.dyj.idle.entity.Order;
import com.dyj.idle.entity.OrderDetail;
import com.dyj.idle.entity.OrderInfo;
import com.dyj.idle.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    public void insertOrder(Long id,Long goods_id,Long buyer,Integer use_coin,double goods_price,double goods_freight,double pay_money,String address,String receive_name,String receive_number){

        orderMapper.insertOrder(id,goods_id,buyer,use_coin,goods_price,goods_freight,pay_money,address,receive_name,receive_number);
    }
    public OrderDetail getOrderDetail(Long orderId){
        return orderMapper.getOrderDetail(orderId);
    }

    public List<OrderInfo> getAllOrder(Long id){
        return orderMapper.getAllOrder(id);
    }

    public Order getOrderById(Long id){
        return orderMapper.getOrderById(id);
    }


    //是否成功更新订单
    public Boolean updateOrder(Long id,Integer state){
        if(orderMapper.updateOrder(id,state)>0){
            return true;
        }else{
            return false;
        }
    }

    //删除订单
    public void deleteOrder(Long id){
        orderMapper.deleteOrder(id);
    }
}
