package com.hzth.myapp.ws;

import java.util.HashSet;
import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Builder;

public class MyServerApplicationConfig implements ServerApplicationConfig {

	@Override
	public Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> endpointClasses) {
		System.out.println("配置ws访问地址");
		Set<ServerEndpointConfig> result = new HashSet<>();
		for (Class<? extends Endpoint> clazz : endpointClasses) {
			if (clazz == EchoEndpoint.class) {
				Builder builder = ServerEndpointConfig.Builder.create(clazz, "/ws/echo");
				result.add(builder.build());
			}
		}
		return result;
	}

	@Override
	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		for (Class<?> clazz : scanned) {
			System.out.println(clazz.getName());
		}
		return scanned;
	}

}
