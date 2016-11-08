package com.drink.cornerstone.mongodb;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by newroc on 14-4-1.
 */
public class GridFsTemplate {
    private MongoConnection mongoConnection;
    private GridFS gridFS;

    /**
     * db名称
     */
    private String dbName;

    /**
     * root collection
     */
    private  String bucket;

    public void init(){
       DB db=mongoConnection.getDB(dbName);
       gridFS = new GridFS(db, bucket);
    }

    public GridFSInputFile saveFile(InputStream input,String fileName,DBObject metaData,String contentType) throws IOException {
        GridFSInputFile fsFile = gridFS.createFile(input,true);
        fsFile.setFilename(fileName);
        fsFile.setMetaData(metaData);
        fsFile.setContentType(contentType);
        fsFile.save();
        if(input!=null){
            input.close();
        }

        return fsFile;
    }

    public GridFSDBFile find(String fileName){
        GridFSDBFile imageForOutput = gridFS.findOne(fileName);
       return imageForOutput;
    }

    public DBCursor getFileList() {
        return gridFS.getFileList();
    }

    public List<GridFSDBFile> find(DBObject query, DBObject sort) {
        return gridFS.find(query, sort);
    }

//    public GridFSInputFile createFile(InputStream in, boolean closeStreamOnPersist) {
//        return gridFS.createFile(in, closeStreamOnPersist);
//    }

    public GridFSDBFile findOne(String filename) {
        return gridFS.findOne(filename);
    }

    public GridFSDBFile findOne(DBObject query) {
        return gridFS.findOne(query);
    }

    public DB getDB() {
        return gridFS.getDB();
    }

    public List<GridFSDBFile> find(String filename, DBObject sort) {
        return gridFS.find(filename, sort);
    }

    public void remove(DBObject query) {
        gridFS.remove(query);
    }

    public GridFSDBFile find(ObjectId id) {
        return gridFS.find(id);
    }

//    public GridFSInputFile createFile() {
//        return gridFS.createFile();
//    }

//    public GridFSInputFile createFile(String filename) {
//        return gridFS.createFile(filename);
//    }

//    public GridFSInputFile createFile(InputStream in, String filename, boolean closeStreamOnPersist) {
//        return gridFS.createFile(in, filename, closeStreamOnPersist);
//    }

    public GridFSDBFile findOne(ObjectId id) {
        return gridFS.findOne(id);
    }

//    public GridFSInputFile createFile(File f) throws IOException {
//        return gridFS.createFile(f);
//    }

    public String getBucketName() {
        return gridFS.getBucketName();
    }

//    public GridFSInputFile createFile(byte[] data) {
//        return gridFS.createFile(data);
//    }
//
//    public GridFSInputFile createFile(InputStream in, String filename) {
//        return gridFS.createFile(in, filename);
//    }

    public DBCursor getFileList(DBObject query) {
        return gridFS.getFileList(query);
    }

    public DBCursor getFileList(DBObject query, DBObject sort) {
        return gridFS.getFileList(query, sort);
    }

    public void remove(ObjectId id) {
        gridFS.remove(id);
    }

//    public GridFSInputFile createFile(InputStream in) {
//        return gridFS.createFile(in);
//    }

    public void remove(String fileName){
        gridFS.remove(fileName);
    }
    public List<GridFSDBFile> find(DBObject query){
         return gridFS.find(query);
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


    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public static void main(String[] args) {
//        File file = new File("/Users/newroc/Pictures/Nikon Transfer 2/20140308/20140308_105552.NEF");
//        GridFsTemplate util=new GridFsTemplate();
//
//        try {
//            InputStream input = new FileInputStream(file);
//            DBObject metaData = new BasicDBObject();
//            metaData.put("extra1", "anything 1");
//            metaData.put("extra2", "anything 2");
//            util.saveFile(input,"filename.png",metaData,"jpg");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

}
