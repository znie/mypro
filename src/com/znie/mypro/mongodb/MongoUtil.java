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
	 * 插入一条记录
	 * 
	 * @param tablename
	 * @param document
	 */
	public void insertOne(String tablename, Document document) {
		getCollection(tablename).insertOne(document);
	}

	/**
	 * 插入多条记录
	 * 
	 * @param tablename
	 * @param document
	 */
	public void insertMany(String tablename, List<Document> documents) {
		getCollection(tablename).insertMany(documents);
	}

	/**
	 * id删除记录
	 * 
	 * @param tablename
	 * @param id
	 */
	public void deleteOne(String tablename, String id) {
		getCollection(tablename).deleteOne(
				new Document("_id", new ObjectId(id)));
	}

	/**
	 * 删除单条记录
	 * 
	 * @param tablename
	 * @param id
	 */
	public void deleteOne(String tablename, Document doc) {
		getCollection(tablename).deleteOne(doc);
	}

	/**
	 * 删除多条记录
	 * 
	 * @param tablename
	 * @param id
	 */
	public void deleteMany(String tablename, Document doc) {
		getCollection(tablename).deleteMany(doc);
	}

	/**
	 * 通过ID更改记录
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
	 * 更改记录
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
	 * 更改记录
	 * 
	 * @param tablename
	 * @param id
	 * @param document
	 */
	public void updateMany(String tablename, Document filter, Document document) {
		getCollection(tablename).updateMany(filter, document);
	}

	/**
	 * 统计数量
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
	 * 找到的第一条数据
	 * 
	 * @param tablename
	 * @param filter
	 */
	public Document findOne(String tablename, Document filter) {
		return findOne(tablename, filter, new Document(), new Document());
	}

	/**
	 * 找到的第一条数据
	 * 
	 * @param tablename
	 * @param filter
	 * @param field
	 *            不需要显示的字段 eg field.put("_id", 0);
	 */
	public Document findOne(String tablename, Document filter, Document field,
			Document sort) {
		FindIterable<Document> findIterable = getCollection(tablename).find(
				filter);
		if (field != null) {
			// 有字段筛选
			findIterable.projection(filter);
		}
		if (sort != null) {
			// 有排序
			findIterable.sort(sort);
		}
		return findIterable.first();
	}

	/**
	 * 找到的第一条数据 bean
	 * 
	 * @param tablename
	 * @param filter
	 * @param field
	 *            不需要显示的字段 eg field.put("_id", 0);
	 */
	public <T> T findOne(String tablename, Document filter, Document field,
			Document sort, Class<T> entity) {
		FindIterable<T> findIterable = getCollection(tablename).find(filter,
				entity);
		if (field != null) {
			// 有字段筛选
			findIterable.projection(filter);
		}
		if (sort != null) {
			// 有排序
			findIterable.sort(sort);
		}
		return findIterable.first();
	}

	/**
	 * 找到列表
	 * 
	 * @param tablename
	 * @param filter
	 */
	public List<Document> findList(String tablename, Document filter,
			Document field, Document sort, Integer start, Integer limit) {
		FindIterable<Document> findIterable = getCollection(tablename).find(
				filter);
		if (field != null) {
			// 有字段筛选
			findIterable.projection(filter);
		}
		if (sort != null) {
			// 有排序
			findIterable.sort(sort);
		}
		if (start != null) {
			// 开始行
			findIterable.skip(start);
		}
		if (limit != null) {
			// 查询限制
			findIterable.limit(limit);
		}
		List<Document> list = convertIterable(findIterable);
		return list;
	}

	/**
	 * 找到列表 并转化为具体实体
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
			// 有字段筛选
			findIterable.projection(filter);
		}
		if (sort != null) {
			// 有排序
			findIterable.sort(sort);
		}
		if (start != null) {
			// 开始行
			findIterable.skip(start);
		}
		if (limit != null) {
			// 查询限制
			findIterable.limit(limit);
		}
		List<T> list = convertIterable2Bean(findIterable);
		return list;
	}

	/**
	 * findIterable 转 list
	 * 
	 * @param findIterable
	 * @return
	 */
	private List<Document> convertIterable(FindIterable<Document> findIterable) {

		final List<Document> list = new ArrayList<Document>();
		// 迭代结果集，并用块(block)运用到每个文档：
		findIterable.forEach(new Block<Document>() {

			@Override
			public void apply(Document document) {
				list.add(document);
			}
		});
		return list;
	}

	/**
	 * findIterable 转 list bean
	 * 
	 * @param findIterable
	 * @return
	 */
	private <T> List<T> convertIterable2Bean(FindIterable<T> findIterable) {

		final List<T> list = new ArrayList<T>();
		// 迭代结果集，并用块(block)运用到每个文档：
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
			//有模糊查询的map
			Map<String,String> blur = (Map<String, String>) document.get("blur");
			for(Entry<String, String> entry : blur.entrySet()){
				//转成模糊查询条件
				Pattern pattern = Pattern.compile("^.*" + entry.getValue() + ".*$");
				document.put(entry.getKey(),pattern);
			}
		}
		document.remove("blur");
//		大于小于 时间查询
//		Document timeQuery = new Document();
//		timeQuery.put("$gte", startDate);
//		timeQuery.put("$lt", endDate);
//		doc.put("date", timeQuery);
		return document;
	}
}
