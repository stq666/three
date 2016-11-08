package com.drink.cornerstone.mongodb;

import com.mongodb.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by newroc on 14-4-1.
 */

public class LogTemplate {
    private MongoConnection mongoConnection;
    private DB db;
    private DBCollection logs;


/**
     * db名称
     */

    private String dbName;



    public void init(){
       db=mongoConnection.getDB(dbName);

    }
    public void save(Log log){
        try {
            logs=db.getCollection(log.getOrgId().toString());
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            String keyDate=df.format(log.getDate());
            DBObject obj=new BasicDBObject();
            obj.put("content",log.getContent());
            obj.put("date",log.getDate());
            obj.put("userName",log.getUserName());
            BasicDBObject document = new BasicDBObject();
            document.put("date", keyDate);
            document.put("data",obj);
            logs.insert(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List findByDate(Long orgId,Date date){
        logs=db.getCollection(orgId.toString());
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String keyDate=df.format(date);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("date", keyDate);
        DBCursor cur=logs.find(searchQuery);

        return cur==null?null:cur.toArray();
    }
    public void deleteByDate(Long orgId,Date date){
        logs=db.getCollection(orgId.toString());
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String keyDate=df.format(date);
        BasicDBObject param = new BasicDBObject();
        param.put("date", keyDate);
        logs.remove(param);
    }


    public DBCollection getLogs() {
        return logs;
    }

    public void setLogs(DBCollection logs) {
        this.logs = logs;
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
