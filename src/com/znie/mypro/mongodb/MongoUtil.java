package com.znie.mypro.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.sun.org.apache.bcel.internal.generic.NEW;

@Component
public class MongoUtil {

	public MongoCollection<Document> getCollection(String tablename) {
		MongoCollection<Document> collections = MongoManager.getMongoDatabase()
				.getCollection(tablename);
		return collections;
	}

	/**
	 * ����һ����¼
	 * 
	 * @param tablename
	 * @param document
	 */
	public void insertOne(String tablename, Document document) {
		getCollection(tablename).insertOne(document);
	}

	/**
	 * ���������¼
	 * 
	 * @param tablename
	 * @param document
	 */
	public void insertMany(String tablename, List<Document> documents) {
		getCollection(tablename).insertMany(documents);
	}

	/**
	 * idɾ����¼
	 * 
	 * @param tablename
	 * @param id
	 */
	public void deleteOne(String tablename, String id) {
		getCollection(tablename).deleteOne(
				new Document("_id", new ObjectId(id)));
	}

	/**
	 * ɾ��������¼
	 * 
	 * @param tablename
	 * @param id
	 */
	public void deleteOne(String tablename, Document doc) {
		getCollection(tablename).deleteOne(doc);
	}

	/**
	 * ɾ��������¼
	 * 
	 * @param tablename
	 * @param id
	 */
	public void deleteMany(String tablename, Document doc) {
		getCollection(tablename).deleteMany(doc);
	}

	/**
	 * ͨ��ID���ļ�¼
	 * 
	 * @param tablename
	 * @param id
	 * @param document
	 */
	public void updateOne(String tablename, String id, Document document) {
		getCollection(tablename).updateOne(
				new Document("_id", new ObjectId(id)), document);
	}

	/**
	 * ���ļ�¼
	 * 
	 * @param tablename
	 * @param id
	 * @param document
	 */
	public void updateOne(String tablename, Document filter, Document document) {
		getCollection(tablename).updateOne(filter,
				new Document("$set", document));
	}

	/**
	 * 
	 * @param tablename
	 * @param id
	 * @param arrName
	 * @param document
	 */
	public void push(String tablename, String id, String arrName,
			Document document) {
		getCollection(tablename).updateOne(
				new Document("_id", new ObjectId(id)),
				new Document("$push", new Document(arrName, document)));
	}

	/**
	 * ���ļ�¼
	 * 
	 * @param tablename
	 * @param id
	 * @param document
	 */
	public void updateMany(String tablename, Document filter, Document document) {
		getCollection(tablename).updateMany(filter, document);
	}

	/**
	 * ͳ������
	 * 
	 * @param tablename
	 * @param filter
	 * @return
	 */
	public Long count(String tablename, Document filter) {
		long total = getCollection(tablename).count(filter);
		return total;
	}

	/**
	 * �ҵ��ĵ�һ������
	 * 
	 * @param tablename
	 * @param filter
	 */
	public Document findOne(String tablename, Document filter) {
		return findOne(tablename, filter, new Document(), new Document());
	}

	/**
	 * �ҵ��ĵ�һ������
	 * 
	 * @param tablename
	 * @param filter
	 * @param field
	 *            ����Ҫ��ʾ���ֶ� eg field.put("_id", 0);
	 */
	public Document findOne(String tablename, Document filter, Document field,
			Document sort) {
		FindIterable<Document> findIterable = getCollection(tablename).find(
				filter);
		if (field != null) {
			// ���ֶ�ɸѡ
			findIterable.projection(filter);
		}
		if (sort != null) {
			// ������
			findIterable.sort(sort);
		}
		return findIterable.first();
	}

	/**
	 * �ҵ��ĵ�һ������ bean
	 * 
	 * @param tablename
	 * @param filter
	 * @param field
	 *            ����Ҫ��ʾ���ֶ� eg field.put("_id", 0);
	 */
	public <T> T findOne(String tablename, Document filter, Document field,
			Document sort, Class<T> entity) {
		FindIterable<T> findIterable = getCollection(tablename).find(filter,
				entity);
		if (field != null) {
			// ���ֶ�ɸѡ
			findIterable.projection(filter);
		}
		if (sort != null) {
			// ������
			findIterable.sort(sort);
		}
		return findIterable.first();
	}

	/**
	 * �ҵ��б�
	 * 
	 * @param tablename
	 * @param filter
	 */
	public List<Document> findList(String tablename, Document filter,
			Document field, Document sort, Integer start, Integer limit) {
		FindIterable<Document> findIterable = getCollection(tablename).find(
				filter);
		if (field != null) {
			// ���ֶ�ɸѡ
			findIterable.projection(filter);
		}
		if (sort != null) {
			// ������
			findIterable.sort(sort);
		}
		if (start != null) {
			// ��ʼ��
			findIterable.skip(start);
		}
		if (limit != null) {
			// ��ѯ����
			findIterable.limit(limit);
		}
		List<Document> list = convertIterable(findIterable);
		return list;
	}

	/**
	 * �ҵ��б� ��ת��Ϊ����ʵ��
	 * 
	 * @param <T>
	 * @param tablename
	 * @param filter
	 */
	public <T> List<T> findList2Bean(String tablename, Document filter,
			Document field, Document sort, Integer start, Integer limit,
			Class<T> entity) {
		FindIterable<T> findIterable = getCollection(tablename).find(filter,
				entity);
		if (field != null) {
			// ���ֶ�ɸѡ
			findIterable.projection(filter);
		}
		if (sort != null) {
			// ������
			findIterable.sort(sort);
		}
		if (start != null) {
			// ��ʼ��
			findIterable.skip(start);
		}
		if (limit != null) {
			// ��ѯ����
			findIterable.limit(limit);
		}
		List<T> list = convertIterable2Bean(findIterable);
		return list;
	}

	/**
	 * findIterable ת list
	 * 
	 * @param findIterable
	 * @return
	 */
	private List<Document> convertIterable(FindIterable<Document> findIterable) {

		final List<Document> list = new ArrayList<Document>();
		// ��������������ÿ�(block)���õ�ÿ���ĵ���
		findIterable.forEach(new Block<Document>() {

			@Override
			public void apply(Document document) {
				list.add(document);
			}
		});
		return list;
	}

	/**
	 * findIterable ת list bean
	 * 
	 * @param findIterable
	 * @return
	 */
	private <T> List<T> convertIterable2Bean(FindIterable<T> findIterable) {

		final List<T> list = new ArrayList<T>();
		// ��������������ÿ�(block)���õ�ÿ���ĵ���
		findIterable.forEach(new Block<T>() {

			@Override
			public void apply(T t) {
				list.add(t);
			}
		});
		return list;
	}
	
	public Document blurDocument(Document document){
		if(document.containsKey("blur")){
			//��ģ����ѯ��map
			Map<String,String> blur = (Map<String, String>) document.get("blur");
			for(Entry<String, String> entry : blur.entrySet()){
				//ת��ģ����ѯ����
				Pattern pattern = Pattern.compile("^.*" + entry.getValue() + ".*$");
				document.put(entry.getKey(),pattern);
			}
		}
		document.remove("blur");
//		����С�� ʱ���ѯ
//		Document timeQuery = new Document();
//		timeQuery.put("$gte", startDate);
//		timeQuery.put("$lt", endDate);
//		doc.put("date", timeQuery);
		return document;
	}
}
