package com.riwcwt.mongo;

import java.net.UnknownHostException;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.riwcwt.ApplicationDocumentTests;

public class MongoTest {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationDocumentTests.class);

	@SuppressWarnings("deprecation")
	@Test
	public void mongo() throws UnknownHostException {
		// 连接到 mongodb 服务
		MongoClient mongoClient = new MongoClient("120.26.109.119", 27017);
		// 连接到数据库
		DB db = mongoClient.getDB("analyse");
		System.out.println("Connect to database successfully");
		boolean auth = db.authenticate("analyse", "DiwandTech201209".toCharArray());
		System.out.println("Authentication: " + auth);
		if (auth) {
			Set<String> names = db.getCollectionNames();
			for (String name : names) {
				logger.info(name);
				DBCollection collection = db.getCollection(name);
				logger.info("数量 ：" + collection.find().count());

				DBCursor cursor = collection.find();
				int n = 0;
				while (cursor.hasNext()) {
					n++;
					if (n > 50) {
						break;
					}
					DBObject row = cursor.next();
					logger.info(row.toString());
				}

			}
			DBCollection modules = db.getCollection("modules");
			DBCursor cursor = modules.find();
			while (cursor.hasNext()) {
				DBObject row = cursor.next();
				logger.info(row.toString());
			}

		}
	}

}
