package com.dyj.idle.component;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dyj.idle.entity.Message;
import com.dyj.idle.service.ChatService;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author websocket服务
 */
@ServerEndpoint(value = "/Chat/{userId}")
@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 记录当前在线连接数
     */
    public static final Map<Long, Session> sessionMap = new ConcurrentHashMap<>();
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {

        sessionMap.put(userId, session);
        System.out.println("有新用户加入，userId="+userId+", 当前在线人数为："+ sessionMap.size());
//        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
//        JSONObject result = new JSONObject();
//        JSONArray array = new JSONArray();
//        result.set("users", array);
//        for (Object key : sessionMap.keySet()) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.set("username", key);
//            // {"username", "zhang", "username": "admin"}
//            array.add(jsonObject);
//        }
////        {"users": [{"username": "zhang"},{ "username": "admin"}]}
//        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") Long userId) {
        System.out.println("用户："+userId+"断开连接");
        sessionMap.remove(userId);
//        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }
    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") Long userId){
        System.out.println("我收到了信息："+message);
        System.out.println(userId);

        JSONObject obj = JSONUtil.parseObj(message);
        //得到消息包的信息
        Long to=Long.parseLong(obj.getStr("to"));
        String content=obj.getStr("content");
        Long cwId=Long.parseLong(obj.getStr("cwId"));
        Integer type=Integer.parseInt(obj.getStr("type"));

        System.out.println("接收的用户id:"+to);
        Session toSession = sessionMap.get(to); // 根据 to用户名来获取 session，再通过session发送消息文本

        if (toSession != null) {

            JSONObject jsonObject = new JSONObject();

            //将Long转为字符串，不然前端会掉精度
            jsonObject.set("sendId", userId.toString());  // from 是 zhang
            jsonObject.set("content", content);  // text 同上面的text
            jsonObject.set("cwId",cwId.toString());
            jsonObject.set("type",type);
            jsonObject.set("receiveId",to.toString());

            this.sendMessage(jsonObject.toString(), toSession);

        } else {
            System.out.println("用户："+userId+"不在线");
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}
