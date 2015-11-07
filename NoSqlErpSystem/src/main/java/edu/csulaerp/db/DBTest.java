package edu.csulaerp.db;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.SECONDS;


public class DBTest {
	// To directly connect to a single MongoDB server (note that this will not auto-discover the primary even
	// if it's a member of a replica set:
	DBTest(){
		try{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB( "mydb" );
			DBCollection coll = db.getCollection("testCollection");
			//mongoClient.setWriteConcern(WriteConcern.JOURNALED);
			BasicDBObject doc = new BasicDBObject("name", "MongoDB")
	        	.append("type", "database")
	        	.append("count", 1)
	        	.append("info", new BasicDBObject("x", 203).append("y", 102));
			coll.insert(doc);
		}
		catch(Exception e){
			
		}
	}

	
}
