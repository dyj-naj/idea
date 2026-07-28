package com.dyj.idle;

import cn.hutool.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

@SpringBootTest
public class rabbitmpTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void send(){
        //设置一个30分钟后的延迟消息队列，取消订单
        String exchangeName="orderStore.direct";

        //转出json对象发送延时消息
        JSONObject jsonObject=new JSONObject();
        jsonObject.set("orderId",1l);
        jsonObject.set("userId",2l);
        jsonObject.set("useCoin",3);

        rabbitTemplate.convertAndSend(exchangeName, "dyj", jsonObject.toString(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("2000");
                return message;
            }
        });
    }
}
