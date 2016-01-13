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
	// �����ļ�
	private ResourceBundle cacheProperties = ResourceBundle.getBundle("cache",
			Locale.getDefault());

	// �������
	private ConcurrentHashMap<String, CachePool> cachePools = new ConcurrentHashMap<String, CachePool>();

	private final static CacheFactroy instance = new CacheFactroy();

	private boolean isLanuch = false;

	public static CacheFactroy getInstance() {
		return instance;
	}

	public CacheFactroy() {
		if (!isLanuch) {
			// ��һ�μ���
			String needLaunchPoolInstance = cacheProperties
					.getString("dbcodes");
			String[] needLaunchPools = needLaunchPoolInstance.split(",");
			for (String instance : needLaunchPools) {
				// ��ȡ����
				String redisHost = cacheProperties
						.getString(instance + "_host");
				Integer redisPort = Integer.valueOf(cacheProperties
						.getString(instance + "_port"));
				Integer redisDB = Integer.valueOf(cacheProperties
						.getString(instance + "_db"));

				// ʵ������������������Hashmap��
				CachePool cachePoolInstance = new CachePool(redisHost,
						redisPort, redisDB);
				cachePoolInstance.launch();
				cachePools.put(instance, cachePoolInstance);
			}
			isLanuch = true;
		}
	}
	
	/**
	 * @Description: �ر��������ӳ�
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
	 * @Description: ��ָ�������ӳ��л�ȡһ������
	 * @param poolName
	 *            ���ӳص�����
	 * @return
	 */
	public Jedis getResource(CachePoolName poolName) {
		String strName = poolName.toString();

		return getJedisResource(strName);
	}
	/**
	 * 
	 * @Description: �����ȡ���Ӳ���
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
	 * @Description: �ͷ�һ������
	 * @param poolName
	 *            �������ڵ����ӳ�
	 * @param jedis
	 *            ����
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
	 * @Description: �����ͷ����Ӳ���
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
