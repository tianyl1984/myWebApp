package com.hzth.myapp.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

	private static JedisPool jedisPool = null;

	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(10);
		config.setMaxWaitMillis(1000);
		config.setTestOnBorrow(true);
		jedisPool = new JedisPool(config, "192.168.20.67", 6379);
	}

	public static Jedis getJedis() {
		return jedisPool.getResource();
	}

}
