package com.drink.cornerstone.mongodb;

import com.mongodb.DBObject;
import org.bson.BSONObject;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by ThinkPad on 2014/12/4.
 */
public class Log implements DBObject {

    public Log(){

    }
    public Log(Date date,String content,String userName,Long orgId){
        this.date=date;
        this.content=content;
        this.userName=userName;
        this.orgId=orgId;
    }

    private Date date;
    private String content;
    private String userName;
    private Long orgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public void markAsPartialObject() {

    }

    @Override
    public boolean isPartialObject() {
        return false;
    }

    @Override
    public Object put(String s, Object o) {
        return null;
    }

    @Override
    public void putAll(BSONObject bsonObject) {

    }

    @Override
    public void putAll(Map map) {

    }

    @Override
    public Object get(String s) {
        return null;
    }

    @Override
    public Map toMap() {
        return null;
    }

    @Override
    public Object removeField(String s) {
        return null;
    }

    @Override
    public boolean containsKey(String s) {
        return false;
    }

    @Override
    public boolean containsField(String s) {
        return false;
    }

    @Override
    public Set<String> keySet() {
        return null;
    }
}
