package com.znie.mypro.redis;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.znie.mypro.redis.ConfigConstant.CachePoolName;

import redis.clients.jedis.Jedis;


public class CacheFactroy {
	// 配置文件
	private ResourceBundle cacheProperties = ResourceBundle.getBundle("cache",
			Locale.getDefault());

	// 多个池子
	private ConcurrentHashMap<String, CachePool> cachePools = new ConcurrentHashMap<String, CachePool>();

	private final static CacheFactroy instance = new CacheFactroy();

	private boolean isLanuch = false;

	public static CacheFactroy getInstance() {
		return instance;
	}

	public CacheFactroy() {
		if (!isLanuch) {
			// 第一次加载
			String needLaunchPoolInstance = cacheProperties
					.getString("dbcodes");
			String[] needLaunchPools = needLaunchPoolInstance.split(",");
			for (String instance : needLaunchPools) {
				// 获取配置
				String redisHost = cacheProperties
						.getString(instance + "_host");
				Integer redisPort = Integer.valueOf(cacheProperties
						.getString(instance + "_port"));
				Integer redisDB = Integer.valueOf(cacheProperties
						.getString(instance + "_db"));

				// 实例化，启动，并塞入Hashmap中
				CachePool cachePoolInstance = new CachePool(redisHost,
						redisPort, redisDB);
				cachePoolInstance.launch();
				cachePools.put(instance, cachePoolInstance);
			}
			isLanuch = true;
		}
	}
	
	/**
	 * @Description: 关闭所有连接池
	 */
	public void shutDown() {
		if (this.cachePools.size() > 0) {
			Iterator<Entry<String, CachePool>> iterator = this.cachePools
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, CachePool> hashEntry = iterator.next();
				CachePool poolInstance = hashEntry.getValue();
				poolInstance.destory();

				cachePools.remove(hashEntry.getKey());
			}

			cachePools.clear();
		}
	}

	/**
	 * @Description: 从指定的链接池中获取一个链接
	 * @param poolName
	 *            连接池的名字
	 * @return
	 */
	public Jedis getResource(CachePoolName poolName) {
		String strName = poolName.toString();

		return getJedisResource(strName);
	}
	/**
	 * 
	 * @Description: 具体获取连接操作
	 * @param instanceName
	 * @return
	 */
	private Jedis getJedisResource(String instanceName) {
		Jedis jedisResource = null;
		CachePool pool = cachePools.get(instanceName);
		if (pool != null) {
			jedisResource = pool.getResource();
		}
		return jedisResource;
	}
	/**
	 * @Description: 释放一个链接
	 * @param poolName
	 *            连接所在的连接池
	 * @param jedis
	 *            连接
	 */
	public void returnResource(CachePoolName poolName, Jedis jedis) {
		if (jedis == null) {
			return;
		}

		String strName = poolName.toString();
		returnJedisResource(strName, jedis);
	}

	/**
	 * 
	 * @Description: 具体释放连接操作
	 * @param poolName
	 * @param jedisInstance
	 */
	private void returnJedisResource(String poolName, Jedis jedisInstance) {
		CachePool pool = cachePools.get(poolName);
		if (pool != null) {
			pool.returnResource(jedisInstance);
		}
	}
}
