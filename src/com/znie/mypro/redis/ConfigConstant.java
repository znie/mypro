package com.znie.mypro.redis;

public class ConfigConstant {

	// 闲置时测试
	public static final int TIMEOUT = 8000;
	
	public static final String PWD = "jedis2015!@$";
	
	// 从数据库连接池中取得连接时，对其的有效性进行检查
	public static final boolean TEST_ON_BORROW = true;

	// 最大连接数
	public static final int MAX_ACTIVE = 36;

	// 最大闲置的连接数
	public static final int MAX_IDLE = 20;

	// 最小.....
	public static final int MIN_IDLE = 5;

	// 请求最长等待时间/毫秒
	public static final int MAX_WAIT = 8000;

	// 闲置时测试
	public static final boolean TEST_WHILE_IDLE = true;
	
	/**
	 * 
	 * @Description: 连接池的名字
	 * @author ziye - ziye[at]mogujie.com
	 * @date 2013-7-21 下午6:37:06
	 * 
	 */
	public enum CachePoolName {
		user ,// 用户
		rs  ,//资源
		xy  , //学院
		ac  , //活动
		cache   //服务器缓存
	}


}
