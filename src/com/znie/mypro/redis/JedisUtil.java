package com.znie.mypro.redis;

import java.util.List;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.istack.internal.Pool;

import redis.clients.jedis.Jedis;


public class JedisUtil {
	
	private static String host = "222.240.205.112";
	private static Integer port = 6179;
	private static Integer db = 4;
	private static CachePool pool = null;
	private static Jedis jedis;
	public JedisUtil() {
		pool = new CachePool(host, port, db);
		pool.launch();
		
	}
	public static void main(String[] args){
		JedisUtil util = new JedisUtil();
		jedis = pool.getResource();
//		System.out.println(util.existsKey("hello"));
		//set
//		jedis.getSet("hello:set", "world2");
//		System.out.println("是否存在key（hello:set）："+jedis.exists("hello:set"));
//		System.out.println("key（hello:set）值"+jedis.get("hello:set"));
		util.testPush();
	
//		hset
//		jedis.hset("hello:hset", "set1", "value2");
//		jedis.hset("hello:hset", "set2", "value1");
		pool.returnResource(jedis);
		pool.destory();
	}

	public void testPush() {
		//push
		//rpush 右边先入库
//		jedis.rpush("hello:push", "world1", "world2", "world3");
		//lpush 左边先入库
//		jedis.lpush("hello:push2", "world1", "world2", "world3");
		System.out.println("rpush长度"+jedis.llen("hello:push"));
		List<String> pushList = jedis.lrange("hello:push", 0, 2);
		System.out.println(pushList);
		// count > 0: 从头往尾移除值为 value 的元素，count为移除的个数。
		// count < 0: 从尾往头移除值为 value 的元素，count为移除的个数。
		// count = 0: 移除所有值为 value 的元素。
		jedis.lrem("hello:push", 1, "world2");
		System.out.println("lpush长度"+jedis.llen("hello:push2"));
		pushList = jedis.lrange("hello:push2", 0, 2);
		System.out.println(pushList);
	}

}
