package com.drink.cornerstone.mongodb;

import com.mongodb.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by newroc on 14-4-1.
 */

public class StatisticTemplate {
    private MongoConnection mongoConnection;
    private DB db;
    private DBCollection collection;


    /**
     * db名称
     */
    private String dbName;



    public void init(){
       db=mongoConnection.getDB(dbName);

    }
    public void save(Statistic statistic,String collectionName){
        try {
            collection=db.getCollection(collectionName);
            DateFormat df=new SimpleDateFormat("yyyy-MM");
            String keyDate=df.format(statistic.getDate());
            DBObject obj=new BasicDBObject();
            obj.put("content",statistic.getObj().toJSONString());
            BasicDBObject document = new BasicDBObject();
            document.put("date", keyDate);
            document.put("data",obj);
            collection.insert(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List findByDate(Long orgId,Date date){
        collection=db.getCollection(orgId.toString());
        DateFormat df=new SimpleDateFormat("yyyy-MM");
        String keyDate=df.format(date);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("date", keyDate);
        DBCursor cur=collection.find(searchQuery);

        return cur==null?null:cur.toArray();
    }
    public void deleteByDate(Long orgId,Date date){
        collection=db.getCollection(orgId.toString());
        DateFormat df=new SimpleDateFormat("yyyy-MM");
        String keyDate=df.format(date);
        BasicDBObject param = new BasicDBObject();
        param.put("date", keyDate);
        collection.remove(param);
    }


    public DBCollection getCollection() {
        return collection;
    }

    public void setCollection(DBCollection collection) {
        this.collection = collection;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public MongoConnection getMongoConnection() {
        return mongoConnection;
    }

    public void setMongoConnection(MongoConnection mongoConnection) {
        this.mongoConnection = mongoConnection;
    }




    public static void main(String[] args) {



    }

}
