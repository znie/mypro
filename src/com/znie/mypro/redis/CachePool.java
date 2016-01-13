package com.znie.mypro.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class CachePool {

	private JedisPool pool = null;
	private JedisPoolConfig poolConfig = null;
	private String host; // ����ķ�����
	private int port; // �˿�
	private int db; // ���ӵ��ڼ���db

	public CachePool(String host, Integer port, Integer db) {
		this.host = host;
		this.port = port;
		this.db = db;
	}

	/**
	 * @Description: ��ʼ������
	 */
	public void initialConfig() {
		if (poolConfig == null) {
			poolConfig = new JedisPoolConfig();
			poolConfig.setTestOnBorrow(ConfigConstant.TEST_ON_BORROW); // �����ݿ����ӳ���ȡ������ʱ���������Ч�Խ��м��
			poolConfig.setMaxTotal(ConfigConstant.MAX_ACTIVE);// ���������
			poolConfig.setMaxIdle(ConfigConstant.MAX_IDLE); // ������õ�������
			poolConfig.setMinIdle(ConfigConstant.MIN_IDLE); // ����
			poolConfig.setMaxWaitMillis(ConfigConstant.MAX_WAIT);// ������ȴ�ʱ��/����
			poolConfig.setTestWhileIdle(ConfigConstant.TEST_WHILE_IDLE); // ����ʱ����
		}
	}

	/**
	 * 
	 * @Description: ��ʼ��
	 */
	public void launch() {
		if (pool == null) {
			initialConfig();
			pool = new JedisPool(poolConfig, this.host, this.port, ConfigConstant.TIMEOUT,
					ConfigConstant.PWD);
		}
	}

	/**
	 * 
	 * @Description: ����
	 */
	public void destory() {
		if (pool != null) {
			pool.destroy();
		}
	}

	/**
	 * 
	 * @Description: �������
	 * @return redis������
	 */
	public Jedis getResource() {
		Jedis jedisInstance = null;
		if (pool != null) {
			jedisInstance = pool.getResource();
			if (db > 0) {
				jedisInstance.select(db);
			}
		}
		return jedisInstance;
	}

	/**
	 * 
	 * @Description: �ͷ�����
	 * @param jedisInstance
	 */
	public void returnResource(Jedis jedisInstance) {
		pool.returnResource(jedisInstance);
	}
}
