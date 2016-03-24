package com.hzth.myapp.activeMQ;

import java.util.Date;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 
 * @author tianyl
 * 
 */
public class QueueMessageDemo {

	private static final String BROKER_URL = "tcp://localhost:61616";

	private static final String QUEUE_NAME = "queue_time";

	public static void main(String[] args) {
		startReceive();
		// startSend();
	}

	private static void startSend() {
		QueueConnection connection = null;
		QueueSession session = null;
		QueueConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
		try {
			connection = connectionFactory.createQueueConnection();
			connection.start();
			// true 使用session，AUTO_ACKNOWLEDGE 自动应答，自动删除消息
			session = connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(QUEUE_NAME);
			QueueSender sender = session.createSender(queue);
			sender.setDeliveryMode(DeliveryMode.PERSISTENT);

			System.out.println("开始发送。。。");
			for (int i = 0; i < 2; i++) {
				TextMessage tm = session.createTextMessage();
				tm.setText("time:" + new Date().getTime());
				Thread.sleep(2000);
				sender.send(tm);
			}
			session.commit();
			System.out.println("发送完成。。。");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(session, connection);
		}
	}

	private static void startReceive() {
		QueueConnection connection = null;
		QueueSession session = null;
		QueueConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, BROKER_URL);
		try {
			connection = connectionFactory.createQueueConnection();
			connection.start();
			// true 使用session，AUTO_ACKNOWLEDGE 自动应答，自动删除消息
			session = connection.createQueueSession(true, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(QUEUE_NAME);
			QueueReceiver receiver = session.createReceiver(queue);
			receiver.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					if (message != null) {
						if (message instanceof TextMessage) {
							TextMessage tm = (TextMessage) message;
							try {
								System.out.println("接收到消息:" + tm.getText());
							} catch (JMSException e) {
								e.printStackTrace();
							}
						}
					}
				}
			});

			Thread.sleep(10 * 1000);
			session.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(session, connection);
		}
	}

	private static void close(QueueSession session, QueueConnection connection) {
		try {
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			System.out.println("关闭异常。。。");
			e.printStackTrace();
		}
	}
}
