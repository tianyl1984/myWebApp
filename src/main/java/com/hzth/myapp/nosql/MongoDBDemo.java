package com.hzth.myapp.nosql;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class MongoDBDemo {

	private static Mongo mongo;

	private static DB db;

	public static void main(String[] args) throws Exception {
		init();
		// showDbNames();
		// showCollections();
		// insertData();
		showAllData();

		close();
	}

	private static void showAllData() {
		DBCollection collection = db.getCollection("user");
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	private static void insertData() {
		DBCollection collection = db.getCollection("user");
		BasicDBObject doc = new BasicDBObject();
		doc.append("name", "张三");
		doc.append("email", "zhangsan@123.com");
		doc.append("regDate", new Date());
		doc.append("wife", new BasicDBObject("name", "Lily").append("email", "lily@123.com"));
		BasicDBList contact = new BasicDBList();
		contact.add(new BasicDBObject("QQ", "123456789"));
		contact.add(new BasicDBObject("tel", "987654321"));
		contact.add(new BasicDBObject("homepage", "http://www.aaa.com"));
		doc.append("contact", contact);
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
		db = mongo.getDB("MongodbDemo");
	}
}
