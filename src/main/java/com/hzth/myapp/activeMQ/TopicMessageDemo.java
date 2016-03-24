package com.hzth.myapp.activeMQ;

import java.util.Date;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicMessageDemo {

	public static final String BROKER_URL = "tcp://localhost:61616";
	public static final String TOPIC_NAME = "topic_time";

	public static void main(String[] args) {
		new Thread(new Receiver()).start();
		new Thread(new Receiver()).start();
		new Thread(new Sender()).start();
	}

}

class Sender implements Runnable {

	TopicConnection connection = null;

	public Sender() {
		TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, TopicMessageDemo.BROKER_URL);
		try {
			connection = factory.createTopicConnection();
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int times = 0;
		while (true) {
			TopicSession session = null;
			try {
				session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
				Topic topic = session.createTopic(TopicMessageDemo.TOPIC_NAME);
				TopicPublisher publisher = session.createPublisher(topic);
				publisher.setDeliveryMode(DeliveryMode.PERSISTENT);
				for (int i = 0; i < 2; i++) {
					times++;
					TextMessage tm = session.createTextMessage();
					String msg = "时间：" + new Date().getTime() + ",个数：" + times;
					tm.setText(msg);
					System.out.println("发送消息：" + msg);
					publisher.send(tm);
					Thread.sleep(500);
				}
				session.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null) {
					try {
						session.close();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Receiver implements Runnable {

	TopicConnection connection = null;

	public Receiver() {
		TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, TopicMessageDemo.BROKER_URL);
		try {
			connection = factory.createTopicConnection();
			connection.start();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			TopicSession session = null;
			try {
				session = connection.createTopicSession(true, Session.AUTO_ACKNOWLEDGE);
				Topic topic = session.createTopic(TopicMessageDemo.TOPIC_NAME);
				TopicSubscriber subscriber = session.createSubscriber(topic);
				subscriber.setMessageListener(new MessageListener() {
					@Override
					public void onMessage(Message message) {
						if (message instanceof TextMessage) {
							TextMessage tm = (TextMessage) message;
							try {
								System.out.println("收到消息：" + tm.getText() + ",线程：" + Thread.currentThread().getId());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				});
				Thread.sleep(2000);
				session.commit();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null) {
					try {
						session.close();
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}