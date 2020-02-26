package com.yylnb.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author RainLin
 * @date 2020/2/24 - 14:49
 */


@ServerEndpoint("/online/{user_id}")
@Component
public class LoginAndMessage {
    @Autowired
    Chat chat;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。必须
    private static Map<Integer, LoginAndMessage> webSocketSet = new ConcurrentHashMap<Integer, LoginAndMessage>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据。必须
    private Session session;

    private Integer user_id;
    private Integer receiver_id;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("user_id") Integer user_id) throws IOException {
        this.session = session;
        this.user_id = user_id;
        this.receiver_id = receiver_id;

        if (webSocketSet.containsKey(user_id)) {
            webSocketSet.remove(user_id);
            webSocketSet.put(user_id, this);
        } else {
            webSocketSet.put(user_id, this);
            System.out.println("当前在线人数:" + getOnlineCount());
            //在线数加1
        }
        //获取当前用户的离线消息
        String id = Integer.toString(user_id);
        List<String> messageList = new ArrayList<String>();
        messageList = chat.getOldMessage(id);
//        String size = String.valueOf(messageList.size());
//        session.getBasicRemote().sendText(size);
        for (String message : messageList) {
            session.getBasicRemote().sendText(message);
        }


    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(user_id);  //从set中删除
        System.out.println("有连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        message(message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void message(String message) throws IOException {
        for (LoginAndMessage item : webSocketSet.values()) {
            if (item.user_id.equals(user_id)) {
                item.session.getBasicRemote().sendText(message);
                break;
            }
        }
    }

    /**
     * 实现服务器主动推送
     */
//    public void sendMessage(String message) throws IOException {
//        this.session.getBasicRemote().sendText(message);
//    }

    //是否在线
    public static synchronized Boolean isOnline(Integer id) {
        Boolean isOnline = false;
        for (LoginAndMessage item : webSocketSet.values()) {
            if (item.user_id.equals(id)) {
                isOnline = true;
                break;
            }
        }
        return isOnline;
    }

    public static synchronized int getOnlineCount() {
        return webSocketSet.size();
    }

}