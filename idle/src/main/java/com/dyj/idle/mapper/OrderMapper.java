package com.dyj.idle.mapper;

import com.dyj.idle.entity.Order;
import com.dyj.idle.entity.OrderDetail;
import com.dyj.idle.entity.OrderInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface OrderMapper {

    @Insert("insert into `order`(id,goods_id,buyer,creat_time,state,use_coin,goods_price,goods_freight,pay_money,address,receive_name,receive_number)\n" +
            "\n" +
            "values(#{id},#{goods_id},#{buyer},NOW(),0,#{use_coin},#{goods_price},#{goods_freight},#{pay_money},#{address},#{receive_name},#{receive_number})")
    void insertOrder(Long id,Long goods_id,Long buyer,Integer use_coin,double goods_price,double goods_freight,double pay_money,String address,String receive_name,String receive_number);


    OrderDetail getOrderDetail(@Param("orderId") Long orderId);

    List<OrderInfo> getAllOrder(@Param("id") Long id);

    @Select("select * from `order` where id=#{id}")
    Order getOrderById(Long id);

    @Update("update `order` set state=#{state} where id=#{id} and state=0")
    Integer updateOrder(Long id,Integer state);

    @Delete("delete from `order` where id=#{id}")
    void deleteOrder(Long id);

}
