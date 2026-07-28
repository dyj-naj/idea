package com.dyj.idle.listeners;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dyj.idle.service.GoodsService;
import com.dyj.idle.service.OrderService;
import com.dyj.idle.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    //这个是订单超时后的监听
    @RabbitListener(queues = "order.queue")
    public void ListenOrderQueue(String msg){
        JSONObject jsonObject= JSONUtil.parseObj(msg);
        Long orderId=Long.parseLong(jsonObject.getStr("orderId"));
        Long userId=Long.parseLong(jsonObject.getStr("userId"));
        Integer useCoin=Integer.parseInt(jsonObject.getStr("useCoin"));
        Long goodsId=Long.parseLong(jsonObject.getStr("goodsId"));
        //调用数据库，将订单设置为取消
        if(orderService.updateOrder(orderId,2)){//成功更新订单
            if(useCoin>0)
            userService.changeCoin(userId,-useCoin);
            goodsService.plusStock(goodsId);
        }
    }
}
