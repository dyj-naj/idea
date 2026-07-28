package com.dyj.idle.service;


import com.dyj.idle.entity.ChatWindow;
import com.dyj.idle.entity.Message;
import com.dyj.idle.mapper.ChatMapper;
import com.dyj.idle.utils.IdWorker;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private GoodsService goodsService;
    public List<ChatWindow> getAllChatWindow(Long userId){
        return chatMapper.getAllChatWindow(userId);
    }


    public void insertMessage(Long cwId, Integer type,Long sendId,Long receiveId,String content){
        chatMapper.insertMessage(cwId,type,sendId,receiveId,content);
    }
    public List<Message> getAllMessage(Long cwId){
        return  chatMapper.getAllMessage(cwId);
    }//得到这个聊天窗口的全部信息


    public Long getChatWindow(Long goodsId, Long seller, Long buyer){

        Long chatWindow = chatMapper.getChatWindow(goodsId, seller, buyer);
        if(chatWindow!=null){
            return chatWindow;//得到了窗口
        }
        //否则重新创建一个聊天窗口
        IdWorker idWorker=new IdWorker(0,0);
        chatWindow=idWorker.nextId();
        goodsService.addGoodsWant(goodsId);//添加商品的想要量
        try {
            chatMapper.insertChatWindow(chatWindow,goodsId,seller,buyer);
        }catch (Exception e){
            System.out.println(e);
        }
        return chatWindow;
    }

    public  void changeGoodsPrice(Long id,double newPrice,String newSend,double newFreight){

        chatMapper.changeGoodsPrice(id,newPrice,newSend,newFreight);
    }
}
