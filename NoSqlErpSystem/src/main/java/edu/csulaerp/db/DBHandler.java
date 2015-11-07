package edu.csulaerp.db;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import edu.csula.common.CommonBase;

public class DBHandler {
	
	private static DBHandler dbHandler;
	private MongoClient mongoClient;
	private DB db;
	
	private DBHandler(){
		try {
			this.mongoClient = new MongoClient( "localhost" , 27017 );
			this.db = mongoClient.getDB( "erpsystem" );
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static DBHandler getDBHandler(){
		if(DBHandler.dbHandler == null){
			DBHandler.dbHandler = new DBHandler();
		}
		return DBHandler.dbHandler;
	}
	
	public int registerUser(String password, String email){
		int bRet = 0;
		DBCollection coll = db.getCollection("users");
		
		 // now use a query to get 1 document out
        BasicDBObject query = new BasicDBObject("email", email);
        DBCursor cursor = coll.find(query);
//        DBObject elemMatch = new BasicDBObject();
//        elemMatch.put("lastName","Smith");
//        elemMatch.put("firstName","John");
//
//        DBObject query = new BasicDBObject();
//        query.append( "type", "AB");
//        query.append( "list", elemMatch);

        try {
        	//Username taken
            if (cursor.hasNext()) {
                System.out.println(cursor.next());
                bRet = -1;
                cursor.close();
            }
            else{
            	bRet = 1;
            }
        } 
        catch(Exception e){
        	bRet = -2;
        }
        finally {
            cursor.close();
        }
        if(bRet > 0){
        	String hashPassword = "";
			try {
				hashPassword = CommonBase.getSaltedHash(password);
				BasicDBObject doc = new BasicDBObject("email", email)
		    		.append("password", hashPassword);
		    	//.append("email", email);
				coll.insert(doc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
        }
	
		
		return bRet;
	}
	public boolean loginUser(String password, String email){
		  boolean bRet=false;
		  try {
		    DBCollection collection=db.getCollection("users");
		    BasicDBObject searchQuery=new BasicDBObject();
		    searchQuery.put("email",email);
		    BasicDBObject keys=new BasicDBObject();
		    keys.put("password",1);
		    DBCursor cursor=collection.find(searchQuery,keys);
		    while (cursor.hasNext()) {
		      BasicDBObject obj=(BasicDBObject)cursor.next();
		      String result="";
		      result+=obj.getString("password");
		      if (CommonBase.check(result, password)) {
		    	  bRet=true;
		      }
		    }
		  }
		 catch (  UnknownHostException e) {
			 e.printStackTrace();
		  }
		catch (  MongoException e) {
			e.printStackTrace();
		  }
		catch (  Exception e) {
		  }
		
		return bRet;
	}

}
