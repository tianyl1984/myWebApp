package com.hzth.myapp.nosql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

public class MongoDBDemo {

	private static Mongo mongo;

	private static DB db;

	public static void main(String[] args) throws Exception {
		init();
		// showDbNames();
		// showCollections();
		// insertData();
		showAllData();
		System.out.println("-----------------");
		// searchData();
		// deleteData();
		pageList();

		close();
	}

	private static void pageList() {
		DBCollection collection = db.getCollection("sms_send_log");
		long count = collection.count();
		System.out.println("共有：" + count);
		DBCursor cursor = collection.find().skip(3).limit(2);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void searchData() {
		DBCollection collection = db.getCollection("user");
		BasicDBObject doc = new BasicDBObject();
		List<String> schools = new ArrayList<String>();
		schools.add("天津第一中学");
		doc.append("schools", new BasicDBObject("$all", schools));
		// doc.append("age", new BasicDBObject("$gt", 32));
		DBCursor cursor = collection.find(doc);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void showAllData() {
		DBCollection collection = db.getCollection("sms_send_log");
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void deleteData() {
		DBCollection collection = db.getCollection("user");
		BasicDBObject dbObj = new BasicDBObject();
		dbObj.append("name", "张三");
		WriteResult result = collection.remove(dbObj);
		System.out.println("error:" + result.getError());
	}

	private static void insertData() {
		DBCollection collection = db.getCollection("user");
		BasicDBObject doc = new BasicDBObject();
		doc.append("name", "李四");
		doc.append("age", 30);
		doc.append("email", "zhangsan@123.com");
		doc.append("regDate", new Date());
		doc.append("wife", new BasicDBObject("name", "Lily").append("email", "lily@123.com"));
		BasicDBList contact = new BasicDBList();
		contact.add(new BasicDBObject("QQ", "123456789"));
		contact.add(new BasicDBObject("tel", "987654321"));
		contact.add(new BasicDBObject("homepage", "http://www.aaa.com"));
		doc.append("contact", contact);
		List<String> schools = new ArrayList<String>();
		// schools.add("天津第一中学");
		// schools.add("天津第二中学");
		// schools.add("天津第三中学");
		// schools.add("河北第四中学");
		// schools.add("河北第五中学");
		schools.add("北京第一中学");
		schools.add("北京第二中学");
		schools.add("北京第三中学");
		schools.add("北京第四中学");

		doc.append("schools", schools);
		collection.insert(doc);
	}

	private static void showCollections() {
		Set<String> names = db.getCollectionNames();
		for (String name : names) {
			System.out.println("Collection:" + name);
		}
	}

	private static void showDbNames() {
		List<String> dbs = mongo.getDatabaseNames();
		for (String name : dbs) {
			System.out.println("DB:" + name);
		}
	}

	private static void close() {
		if (mongo != null) {
			mongo.close();
		}
	}

	private static void init() throws Exception {
		mongo = new Mongo("127.0.0.1", 27017);

		DB adminDb = mongo.getDB("admin");
		boolean flag = adminDb.authenticate("hzth", "hzth-801".toCharArray());
		System.out.println(flag);

		// db = mongo.getDB("MongodbDemo");
		db = mongo.getDB("sms");
		db.collectionExists("aaa");
	}
}
