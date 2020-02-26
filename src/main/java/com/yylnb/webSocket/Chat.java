package com.yylnb.webSocket;

import com.yylnb.dto.Message;
import com.yylnb.service.UserService;
import com.yylnb.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * @author RainLin
 * @date 2020/2/24 - 16:43
 */
@ServerEndpoint("/chat/{sender_id}/{receiver_id}")
@Slf4j
@Component
public class Chat {

    @Autowired
    LoginAndMessage loginAndMessage;
    @Autowired
    UserService userService;
    @Autowired
    GsonUtil gsonUtil;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。必须
    private static Map<String, Session> webSocketSession = new HashMap<String, Session>();
    private static Map<String, List<String>> oldMessage = new HashMap<String, List<String>>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据。必须
    private Session session;

    private Integer sender_id;
    private Integer receiver_id;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("sender_id") Integer sender_id,
                       @PathParam("receiver_id") Integer receiver_id) throws IOException {
        this.session = session;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;

//        messageToYou(message("我已连接..."));

        //在线
        if (webSocketSession.containsKey(sender_id + "to" + receiver_id)) {
            webSocketSession.remove(sender_id + "to" + receiver_id);
            webSocketSession.put(sender_id + "to" + receiver_id, session);
        } else {
            webSocketSession.put(sender_id + "to" + receiver_id, session);
        }

        //判断对方在线
        if (this.webSocketSession.containsKey(receiver_id + "to" + sender_id)) {
//            messageToMe(message("对方正在连接中..."));
        } else {
//            messageToMe(message("对方未进入聊天"));
        }
        if (!loginAndMessage.isOnline(receiver_id)) {
            messageToMe(message("对方不在线,消息会在其上线时提醒!"));
        }
        //查询对方是否给我发信息
        //1.先查询自己有没有未读信息
        if (oldMessage.containsKey(this.sender_id.toString())) {
            //2.如果有
            List<String> messageList = new ArrayList<String>();
            messageList = oldMessage.get(this.sender_id.toString());
            //3. 遍历list找到对方给我发的未读信息
            for (int i = 0; i < messageList.size(); i++) {
                Message msg = gsonUtil.GsonToBean(messageList.get(i), Message.class);
                //4.找到之后加入新的list 并删除旧的
                if (msg.getSend_id().equals(this.receiver_id.toString())) {
                    messageToYou(messageList.get(i));
                    //为了防止循环出现问题，先将发送完毕的信息暂时赋值为null 稍后统一删除
                    messageList.set(i, null);
                }
            }
            //统一删除null
            messageList.removeAll(Collections.singleton(null));
            //判断是否还有未读消息 没有则删除map
            if (messageList.size() == 0) {
                oldMessage.remove(this.sender_id.toString());
            }


        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSession.remove(sender_id + "to" + receiver_id);  //删除
//        messageToYou(message("我已断开..."));
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        messageToYou(message);
        messageToMe(message);
    }

    /**
     * 给对方发
     *
     * @param message
     */
    public void messageToYou(String message) throws IOException {
        //json转换为对象
        Message message1 = gsonUtil.GsonToBean(message, Message.class);
        //先判断对方有没有打开和自己的聊天界面
        if (this.webSocketSession.containsKey(message1.getReceiver_id() + "to" + message1.getSend_id())) {
            //有打开则直接发送
            Session session = this.webSocketSession.get(message1.getReceiver_id() + "to" + message1.getSend_id());
            session.getBasicRemote().sendText(message);
        } else {
            List<String> messageList = new ArrayList<String>();
            //这里是不在线用户 接收不了的信息

            //第一步将这些接收不到的信息追加
            //第二步每次开启连接服务器将未收取的信息发送过来
            //第三步再没有接收的时候提醒

            //先判断有没有未接收的信息
            if (this.oldMessage.containsKey(message1.getReceiver_id())) {
                //如果有 先取出所有信息
                messageList = this.oldMessage.get(message1.getReceiver_id());
                messageList.add(message);
                this.oldMessage.put(message1.getReceiver_id(), messageList);
            } else {
                //如果没有
                messageList.add(message);
                this.oldMessage.put(message1.getReceiver_id(), messageList);
            }


        }

    }

    /**
     * 给自己发
     *
     * @param message
     */
    public void messageToMe(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    public String message(String message) {
        Message msg = new Message();
        //-1为通知
        msg.setReceiver_id("-1");
        msg.setSend_id(sender_id.toString());
        msg.setMsg(message);
        return gsonUtil.BeanToJson(msg);
    }

    public static synchronized List<String> getOldMessage(String id) {
        List<String> messageList = new ArrayList<String>();
        //先接收未读消息
        if (oldMessage.containsKey(id)) {
            messageList = oldMessage.get(id);
        }
        return messageList;
    }

    /**
     * @param error
     */
    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }


}
