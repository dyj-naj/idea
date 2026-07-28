package com.dyj.idle.mapper;

import com.dyj.idle.entity.ChatWindow;
import com.dyj.idle.entity.Message;
import com.dyj.idle.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ChatMapper {
    List<ChatWindow> getAllChatWindow(@Param("userId") Long userId);//得到所有的聊天窗口

    @Insert("insert into chat_message(cw_id,type,send_id,receive_id,content,send_time) values (#{cwId},#{type},#{sendId},#{receiveId},#{content},NOW())")
    void insertMessage(Long cwId, Integer type,Long sendId,Long receiveId,String content);
    @Select("select * from chat_message where cw_id=#{cwId}")
    List<Message> getAllMessage(Long cwId);//得到这个聊天窗口的全部信息

    @Select("SELECT id from chat_window where seller=#{seller} and buyer=#{buyer} and goods_id=#{goodsId}")
    Long getChatWindow(Long goodsId, Long seller, Long buyer);

    void insertChatWindow(@Param("id") Long id,@Param("goodsId")  Long goodsId,@Param("seller")  Long seller,@Param("buyer")  Long buyer);

    @Update("update chat_window set  goods_price=#{newPrice},goods_send=#{newSend},goods_freight=#{newFreight} where id=#{id}")
    void changeGoodsPrice(Long id,double newPrice,String newSend,double newFreight);
}
