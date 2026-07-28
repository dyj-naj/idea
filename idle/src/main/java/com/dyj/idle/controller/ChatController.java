package com.dyj.idle.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dyj.idle.common.R;
import com.dyj.idle.entity.ChatWindow;
import com.dyj.idle.entity.Message;
import com.dyj.idle.service.ChatService;
import com.dyj.idle.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("getChatList")
    public R<List<ChatWindow>> getChatList(Long userId){
        //得到所有私聊列表
        try{
            List<ChatWindow> allChatWindow = chatService.getAllChatWindow(userId);
            return R.success(allChatWindow);
        }catch (Exception e){
            return R.error("私聊列表得到错误");
        }
    }

    //保存聊天记录
    @GetMapping("saveChatRecord")
    public R<String> saveChatRecord(String msg){
        System.out.println("收到保存消息的请求");
        try{
            JSONObject obj = JSONUtil.parseObj(msg);
            //得到消息包的信息
            Long sendId=Long.parseLong(obj.getStr("from"));
            String content=obj.getStr("content");
            Long cwId=Long.parseLong(obj.getStr("cwId"));
            Long receiveId=Long.parseLong(obj.getStr("to"));
            Integer type=Integer.parseInt(obj.getStr("type"));

            chatService.insertMessage(cwId,type,sendId,receiveId,content);
            return R.success("消息保存成功");
        }catch (Exception e){
            return R.error("消息保存错误");
        }
    }

    //得到这个窗口所有聊天信息
    @GetMapping("getAllMessage")
    public R<List<Message>> getAllMessage(Long cwId) {
        System.out.println("获得窗口：" + cwId + "的所有聊天信息");
        try {
            List<Message> allMessage = chatService.getAllMessage(cwId);
            return R.success(allMessage);
        } catch (Exception e) {
            return R.error("消息保存错误");
        }
    }

    //得到这个窗口所有聊天信息
    @GetMapping("getChatWindow")
    public R<Long> getChatWindow(@RequestParam("goodsId") Long goodsId,
                                 @RequestParam("seller") Long seller,
                                 @RequestParam("buyer") Long buyer) {
        try {
            //查找数据库是否有这个窗口(没有就重新创建一个)
            Long chatWindow = chatService.getChatWindow(goodsId, seller, buyer);
            return R.success(chatWindow);
        } catch (Exception e) {
            return R.error("后端错误");
        }
    }

    //保存这个窗口修改的价格
    @GetMapping("changeGoodsPrice")
    public R<String> changeGoodsPrice(@RequestParam("cwId") Long cwId,
                                 @RequestParam("newPrice") double newPrice,
                                 @RequestParam("newSend") String newSend,
                                      @RequestParam("newFreight") double newFreight) {
        try {
            chatService.changeGoodsPrice(cwId,newPrice,newSend,newFreight);
            return R.success("成功");
        } catch (Exception e) {
            return R.error("后端错误");
        }
    }
}
