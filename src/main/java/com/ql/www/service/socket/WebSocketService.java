package com.ql.www.service.socket;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketService extends TextWebSocketHandler {
    private static final ConcurrentHashMap<Long, WebSocketSession> SESSIONS = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
		Long userId = (Long) session.getAttributes().get("userId");
		SESSIONS.put(userId, session);
		log.info("websocket afterConnectionEstablished --> {}", userId);
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		Long userId = (Long) session.getAttributes().get("userId");
		SESSIONS.remove(userId);
		log.info("websocket afterConnectionClosed --> {}", userId);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
		log.info("websocket handleTextMessage --> {}", message.toString());
    }

	public void broadcast(String message) {
		for (WebSocketSession session : SESSIONS.values()) {
			if (session.isOpen()) {
				try {
					session.sendMessage(new TextMessage(message));
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	public void sendMessage(String message, Long... ids) {
		for (Long id : ids) {
			WebSocketSession session = SESSIONS.get(id);
			if (session != null && session.isOpen()) {
				try {
					log.info("websocket sendMessage --> {}", id);
					session.sendMessage(new TextMessage(message));
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	public void sendMessage(Object message, Long... ids) {
		sendMessage(JSON.toJSONString(message), ids);
	}
}

