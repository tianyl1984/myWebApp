package com.hzth.myapp.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClientDemo {

	public static void main(String[] args) throws Exception {
		TTransport transport = new TSocket("127.0.0.1", 8888);
		TProtocol protocol = new TBinaryProtocol(transport);
		UserService.Client client = new UserService.Client(protocol);
		transport.open();

		UserProfile userProfile = client.get(123);
		System.out.println(userProfile.getName() + ":" + userProfile.getEmail());
		UserProfile userProfile2 = new UserProfile();
		userProfile2.setId(1);
		userProfile2.setName("李四");
		userProfile2.setEmail("lisi@123.com");
		client.store(userProfile2);
		System.out.println("client exit!");
	}
}
