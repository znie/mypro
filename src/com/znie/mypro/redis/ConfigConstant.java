package com.znie.mypro.redis;

public class ConfigConstant {

	// ����ʱ����
	public static final int TIMEOUT = 8000;
	
	public static final String PWD = "jedis2015!@$";
	
	// �����ݿ����ӳ���ȡ������ʱ���������Ч�Խ��м��
	public static final boolean TEST_ON_BORROW = true;

	// ���������
	public static final int MAX_ACTIVE = 36;

	// ������õ�������
	public static final int MAX_IDLE = 20;

	// ��С.....
	public static final int MIN_IDLE = 5;

	// ������ȴ�ʱ��/����
	public static final int MAX_WAIT = 8000;

	// ����ʱ����
	public static final boolean TEST_WHILE_IDLE = true;
	
	/**
	 * 
	 * @Description: ���ӳص�����
	 * @author ziye - ziye[at]mogujie.com
	 * @date 2013-7-21 ����6:37:06
	 * 
	 */
	public enum CachePoolName {
		user ,// �û�
		rs  ,//��Դ
		xy  , //ѧԺ
		ac  , //�
		cache   //����������
	}


}
