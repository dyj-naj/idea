package com.dyj.idle.controller;

import cn.hutool.json.JSONObject;
import com.alipay.api.domain.Goods;
import com.dyj.idle.common.R;
import com.dyj.idle.entity.Order;
import com.dyj.idle.entity.OrderDetail;
import com.dyj.idle.service.GoodsService;
import com.dyj.idle.service.OrderService;
import com.dyj.idle.service.UserService;
import com.dyj.idle.utils.IdWorker;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserService userService;

    @PostMapping("submitOrder")
    public R<Long> submitOrder(@RequestParam("userId") Long userId,
                                 @RequestParam("goodsId") Long goodsId,
                                 @RequestParam("receiveName") String receiveName,
                                 @RequestParam("receivePhoneNumber") String receivePhoneNumber,
                                 @RequestParam("address") String address,
                                 @RequestParam("goodsPrice") double goodsPrice,
                                 @RequestParam("goodsFreight") double goodsFreight,
                                 @RequestParam("resPay") double resPay,
                                 @RequestParam("useCoin") Integer useCoin){
        try{
            //先查找这个商品的库存是否不为0
            Boolean successBuy = goodsService.isSuccessBuy(goodsId);
            System.out.println(successBuy);
            if(!successBuy) {
                return R.error("此商品库存已经卖光了");
            }

            //减少用户使用的小猪币
            if(useCoin>0){
                userService.changeCoin(userId,useCoin);
            }
            IdWorker idWorker=new IdWorker(0,0);
            Long orderId=idWorker.nextId();
            orderService.insertOrder(orderId,goodsId,userId,useCoin,goodsPrice,goodsFreight,resPay,address,receiveName,receivePhoneNumber);
            //设置一个30分钟后的延迟消息队列，取消订单
            String exchangeName="orderStore.direct";

            //转出json对象发送延时消息
            JSONObject jsonObject=new JSONObject();
            jsonObject.set("orderId",orderId);
            jsonObject.set("userId",userId);
            jsonObject.set("useCoin",useCoin);
            jsonObject.set("goodsId",goodsId);

            rabbitTemplate.convertAndSend(exchangeName, "dyj", jsonObject.toString(), new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setExpiration("1800000");
                    return message;
                }
            });

            return R.success(orderId);

        }catch (Exception e){
            return R.error("购买错误，请稍后重试");
        }
    }

    @GetMapping("getOrderDetail")
    public R<OrderDetail> getOrderDetail(Long orderId){
        try{
            return R.success(orderService.getOrderDetail(orderId));
        }catch (Exception e){
            return R.error("系统繁忙，请稍后再试");
        }
    }
    @GetMapping("cancelOrder")
    public R<String> cancelOrder(Long orderId){
        try{
            //取消订单业务执行
            orderService.updateOrder(orderId,2);
            Order order = orderService.getOrderById(orderId);
            //返回库存
            goodsService.plusStock(order.getGoodsId());
            //返回积分
            userService.changeCoin(order.getBuyer(),-order.getUseCoin());

            return R.success("成功");
        }catch (Exception e){
            return R.error("系统繁忙，请稍后再试");
        }
    }

}
