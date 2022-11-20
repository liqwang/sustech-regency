package com.sustech.regency.component;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通知前端订单是否支付的{@link javax.websocket.Endpoint EndPoint}
 */
@ServerEndpoint("/websocket/order/{id}")
@Component
public class OrderWebSocket {

	/**
	 * orderId->session
	 */
	private static final Map<Long,Session> SESSIONS = new ConcurrentHashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("id") Long orderId){
		SESSIONS.put(orderId,session);
	}

	@OnClose
	public void onClose(@PathParam("id") Long orderId){
		SESSIONS.remove(orderId);
	}

	/**
	 * 通知前端订单已支付
	 * @param orderId 已经支付的订单id
	 */
	@SneakyThrows(IOException.class)
	public static void notifyFrontend(Long orderId){
		SESSIONS.get(orderId).getBasicRemote().sendText("ok");
	}
}
