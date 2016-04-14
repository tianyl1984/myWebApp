package com.hzth.myapp.redis;

import redis.clients.jedis.Jedis;

/**
 * redis的demo
 * 
 * @author tianyl
 * 
 */
public class RedisDemo {

	public static void main(String[] args) {
		Jedis jedis = RedisUtil.getJedis();
		stringDemo(jedis);
		jedis.close();
	}

	private static void stringDemo(Jedis jedis) {
		jedis.set("strkey", "abc中文");
		System.out.println(jedis.get("strkey"));
	}

}
