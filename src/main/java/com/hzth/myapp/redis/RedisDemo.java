package com.hzth.myapp.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

import com.hzth.myapp.core.util.ThreadUtil;

/**
 * redis的demo
 * 
 * @author tianyl
 * 
 */
public class RedisDemo {

	public static void main(String[] args) throws Exception {
		Jedis jedis = RedisUtil.getJedis();
		// dbDemo(jedis);
		// keyDemo(jedis);
		// stringDemo(jedis);
		// listDemo(jedis);
		// setDemo(jedis);
		// hashDemo(jedis);
		blockDemo(jedis);
		jedis.close();
	}

	private static void blockDemo(final Jedis jedis) {
		final String key = "mqDemo";
		jedis.del(key);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Jedis jedis = RedisUtil.getJedis();
				ThreadUtil.sleep(2000);
				System.out.println("添加数据");
				jedis.lpush(key, "msg10000");
				jedis.close();
				System.out.println("完成添加数据");
			}
		}).start();
		List<String> msgs = jedis.brpop(10, key);
		if (msgs != null) {
			System.out.println("读取到数据");
			// msgs中0为key1为value
			for (String str : msgs) {
				System.out.println(str);
			}
		} else {
			System.out.println("msgs is empry!");
		}
	}

	private static void hashDemo(Jedis jedis) {
		String key = "hashDemo";
		jedis.del(key);
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "aaaaaa");
		map.put("b", "bbbbbb");
		map.put("c", "cccccc");
		jedis.hmset(key, map);
		jedis.hset(key, "d", "dddddd");
		printHash(jedis, key);

	}

	private static void printHash(Jedis jedis, String key) {
		System.out.println("-----------start-----------");
		Map<String, String> map = jedis.hgetAll(key);
		for (String mapkey : map.keySet()) {
			System.out.println(mapkey + ":" + map.get(mapkey));
		}
		System.out.println("------------end------------");
	}

	private static void setDemo(Jedis jedis) {
		String key = "setDemo";
		jedis.del(key);
		jedis.sadd(key, "a", "b", "c");
		printSet(jedis, key);
		jedis.sadd(key, "d", "a");
		printSet(jedis, key);
		System.out.println(jedis.scard(key));
		System.out.println(jedis.sismember(key, "b"));
	}

	private static void printSet(Jedis jedis, String key) {
		System.out.println("-----------start-----------");
		Set<String> values = jedis.smembers(key);
		for (String val : values) {
			System.out.print(val + "\t");
		}
		System.out.println();
		System.out.println("------------end------------");
	}

	private static void listDemo(Jedis jedis) {
		String key = "listDemo";
		jedis.del(key);
		jedis.lpush(key, "aaaa", "b", "c");
		printList(jedis, key);
		jedis.lpush(key, "d");
		printList(jedis, key);
		System.out.println("pop:" + jedis.lpop(key));
		printList(jedis, key);
		jedis.lset(key, 0, "e");
		printList(jedis, key);
	}

	private static void printList(Jedis jedis, String key) {
		System.out.println("-----------start-----------");
		List<String> values = jedis.lrange(key, 0, -1);
		for (String val : values) {
			System.out.print(val + "\t");
		}
		System.out.println();
		System.out.println("------------end------------");
	}

	private static void keyDemo(Jedis jedis) throws Exception {
		System.out.println("是否存在:" + jedis.exists("strkey"));
		jedis.expire("strkey", 1);
		TimeUnit.SECONDS.sleep(2);
		System.out.println("是否存在:" + jedis.exists("strkey"));
		System.out.println("所有key:");
		Set<String> keys = jedis.keys("*");
		for (String key : keys) {
			System.out.println(key);
		}
	}

	private static void dbDemo(Jedis jedis) {
		System.out.println("选择库：" + jedis.select(2));
		System.out.println("清空：" + jedis.flushDB());
	}

	private static void stringDemo(Jedis jedis) {
		jedis.set("strkey", "abc中文");
		System.out.println(jedis.get("strkey"));
		jedis.set("intkey", "1");
		jedis.incrBy("intkey", 5);
		System.out.println(jedis.get("intkey"));
	}

}
