package com.znie.mypro.redis;

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
		util.set("hello:set","world");
//		System.out.println(util.existsKey("hello"));
//		util.push("hello:push", "world1", "world2", "world3");
		pool.destory();
	}
	/**
	 * 判断是否存在key
	 * @param key
	 * @return
	 */
	public boolean existsKey(String key){
		jedis = pool.getResource();
		boolean flag = jedis.exists(key);
		pool.returnResource(jedis);
		return flag;
	}
	/**
	 * set key的值value
	 * @param key
	 * @param value
	 */
	public void set(String key,String value) {
		jedis = pool.getResource();
		//往key中set
		jedis.set(key, value);
		pool.returnResource(jedis);
	}
	/**
	 * push key的值value
	 * @param key
	 * @param value
	 */
	public void push(String key,String... value) {
		jedis = pool.getResource();
		//往key中set
		jedis.rpush(key, value);
		pool.returnResource(jedis);
	}

}
