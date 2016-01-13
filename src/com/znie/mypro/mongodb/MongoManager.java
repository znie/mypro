package com.znie.mypro.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;



import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
/**
 *  mongodb���ӳع����� 
 * @author znie
 *
 */
public class MongoManager {

	// �����ļ�
	private ResourceBundle mongoProperties = ResourceBundle.getBundle("mongo",
			Locale.getDefault());

	private final static MongoManager instance = new MongoManager();

	private static MongoClient mg = null;

	private static MongoDatabase mongoDatabase;

	public static MongoManager getInstance() {
		return instance;
	}

	public static MongoDatabase getMongoDatabase() {
		return mongoDatabase;
	}

	public static void setMongoDatabase(MongoDatabase mongoDatabase) {
		MongoManager.mongoDatabase = mongoDatabase;
	}

	public MongoManager() {
//		System.out.println(mongoDatabase.getName());
		// ���ӳ�����
		String connectionsPerHost = mongoProperties
				.getString("connectionsPerHost");
		// ���ȴ�ʱ��
		String maxWaitTime = mongoProperties.getString("maxWaitTime");
		// scoket��ʱʱ��
		String socketTimeout = mongoProperties.getString("socketTimeout");
		// �������ӳ������ʱ��
		String maxConnectionLifeTime = mongoProperties
				.getString("maxConnectionLifeTime");
		// ���ӳ�ʱʱ��
		String connectTimeout = mongoProperties.getString("connectTimeout");

		MongoClientOptions options = MongoClientOptions.builder()
				.connectionsPerHost(Integer.parseInt(connectionsPerHost))
				.maxWaitTime(Integer.parseInt(maxWaitTime))
				.socketTimeout(Integer.parseInt(socketTimeout))
				.maxConnectionLifeTime(Integer.parseInt(maxConnectionLifeTime))
				.connectTimeout(Integer.parseInt(connectTimeout)).build();

		String hostLen = mongoProperties.getString("len");

		//��������
		List<ServerAddress> hosts = new ArrayList<ServerAddress>();
		for (int i = 1; i <= Integer.parseInt(hostLen); i++) {
			String host = mongoProperties.getString("host" + i);
			// String port = mongoProperties.getString("port"+i);
			hosts.add(new ServerAddress(host));
		}
		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		if (mongoProperties.getString("authentication").equals("1")) {
			// ��Ҫ��֤
			MongoCredential credential = MongoCredential.createCredential(
					mongoProperties.getString("userName"), mongoProperties
							.getString("adminDb"),
					mongoProperties.getString("pwd").toCharArray());
			credentials.add(credential);
		}
		mg = new MongoClient(hosts, credentials, options);
		mongoDatabase = mg.getDatabase(mongoProperties.getString("dbname"));
	}
}
