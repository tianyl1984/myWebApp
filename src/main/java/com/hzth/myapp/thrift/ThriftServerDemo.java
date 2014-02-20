package com.hzth.myapp.thrift;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;

import com.hzth.myapp.thrift.UserService.Iface;

public class ThriftServerDemo {

	public static void main(String[] argument) throws Exception {
		UserService.Processor<Iface> processor = new UserService.Processor<UserService.Iface>(new UserServiceImpl());
		TServerTransport serverTransport = new TServerSocket(8888);
		Args args = new Args(serverTransport);
		args.processor(processor);
		args.protocolFactory(new TBinaryProtocol.Factory(true, true));
		args.transportFactory(new TTransportFactory());
		TServer server = new TThreadPoolServer(args);
		System.out.println("server begin...");
		server.serve();
	}
}

class UserServiceImpl implements Iface {

	@Override
	public void store(UserProfile user) throws TException {
		System.out.println("store:" + user.getName());
	}

	@Override
	public UserProfile get(int id) throws TException {
		System.out.println("get");
		UserProfile result = new UserProfile();
		result.setId(id);
		result.setName("张三");
		result.setEmail("zhangsan@123.com");
		return result;
	}

}
