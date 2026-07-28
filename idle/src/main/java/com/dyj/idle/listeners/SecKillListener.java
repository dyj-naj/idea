package com.dyj.idle.listeners;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dyj.idle.service.GoodsService;
import com.dyj.idle.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecKillListener {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @RabbitListener(queues = "seckill.queue")
    public void ListenSecKillQueue(String msg){
        JSONObject jsonObject=JSONUtil.parseObj(msg);
        Long userId=Long.parseLong(jsonObject.getStr("userId"));
        Long id=Long.parseLong(jsonObject.getStr("id"));
        Integer value=Integer.valueOf(jsonObject.getStr("value"));
        //增加小猪币
        userService.changeCoin(userId,-value);
        //秒杀的商品数减一
        goodsService.subSecKillStore(id);
    }
}
