package com.music.common.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.music.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint("/websocket/{userName}")
@Component
public class WebSocketServer {
      /**
       * *  这里使用静态，让 service 属于类
       */
    private static RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate){
        WebSocketServer.redisTemplate = redisTemplate;
    }

    /**
     * 当前在线连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * 用来存放每个客户端对应的 WebSocketServer 对象
     */
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 接收 userId
     */
    private String userName = "";

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized AtomicInteger getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount.getAndIncrement();
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount.getAndDecrement();
    }

    /**
     * 连接建立成功调用方法
     * @param session
     * @param userName
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName){
        this.session = session;
        this.userName = userName;
        //将userName存入redis
        String messageDto = (String) redisTemplate.opsForValue().get(userName);

        //重新覆盖webSocketMap中的消息
        if(webSocketMap.containsKey(userName)){
            webSocketMap.remove(userName);
            webSocketMap.put(userName, this);
        }else{
            webSocketMap.put(userName, this);
            addOnlineCount();
        }

        log.info("用户连接：" + userName + ",当前在线人数：" + getOnlineCount());
        if(messageDto != null){
            try {
                sendMessage(messageDto);
            } catch (IOException e) {
                log.error("出现错误：", e);
                e.printStackTrace();
            }
        }
        log.info("Connect successful");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        if(webSocketMap.containsKey(userName)){
            webSocketMap.remove(userName);
            subOnlineCount();
        }
        log.info("用户退出:" + userName + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        JSONObject jsonObject = JSON.parseObject(message);
        log.info("对前端传来该用户的音乐进度做保存");
        redisTemplate.opsForValue().set(userName, jsonObject.toJSONString());

    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        log.info("发生错误");
        error.printStackTrace();
    }

}
