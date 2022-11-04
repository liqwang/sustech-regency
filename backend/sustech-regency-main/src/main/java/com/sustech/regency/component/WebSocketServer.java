package com.sustech.regency.component;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessionMap.put(username, session);
        log.info("有新用户加入, username={}, 当前在线人数为: {}", username, sessionMap.size());
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessionMap.remove(username);
        log.info("用户{}下线, 当前在线人数为: {}", username, sessionMap.size());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        log.info("服务端收到用户{}的消息: {}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("dst");
        String text = obj.getStr("text");
        String time = obj.getStr("time");
        Session toSession = sessionMap.get(toUsername);
        if (toSession != null) {
            JSONObject response = new JSONObject();
            response.set("src", username);
            response.set("text", text);
            response.set("time", time);
            this.sendMessage(response.toString(), toSession);
            log.info("发送给用户username={}, 消息: {}", toUsername, response);
        } else {
            log.info("发送失败, 未找到用户username={}的session", toUsername);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message, Session toSession) {
        log.info("服务器给客户端[{}]发送消息: {}", toSession.getId(), message);
        try {
            toSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}