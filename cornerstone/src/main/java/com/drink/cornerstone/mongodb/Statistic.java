package com.drink.cornerstone.mongodb;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.DBObject;
import org.bson.BSONObject;

import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by ThinkPad on 2015/4/14.
 */
public class Statistic implements DBObject {

    private Date date;
    private JSONObject obj;



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public JSONObject getObj() {
        return obj;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
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
