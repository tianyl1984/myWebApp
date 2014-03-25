package com.hzth.myapp.ws;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

import com.hzth.myapp.core.util.DateUtil;

public class EchoEndpoint extends Endpoint {

	public static final List<Session> SESSIONS = Collections.synchronizedList(new ArrayList<Session>());

	static {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("共有连接：" + SESSIONS.size());
				for (Session session : SESSIONS) {
					System.out.println("发送消息：" + session.getId());
					String str = DateUtil.getCurrentDate();
					Basic basic = session.getBasicRemote();
					try {
						basic.sendText(str);
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("发送失败：" + e.getMessage());
					}
				}
			}
		}, new Date(), 1000 * 10);
	}

	@Override
	public void onOpen(Session session, EndpointConfig config) {
		SESSIONS.add(session);
		session.addMessageHandler(new EchoMessageHandler(session));
	}

	@Override
	public void onClose(Session session, CloseReason closeReason) {
		System.out.println("Close:" + session.getId());
		SESSIONS.remove(session);
	}

	@Override
	public void onError(Session session, Throwable thr) {
		System.out.println("Error:" + session.getId());
		SESSIONS.remove(session);
	}

	private static class EchoMessageHandler implements MessageHandler.Whole<String> {

		private Session session;

		public EchoMessageHandler(Session session) {
			this.session = session;
		}

		@Override
		public void onMessage(String message) {
			System.out.println("message:" + message);
		}

	}
}
