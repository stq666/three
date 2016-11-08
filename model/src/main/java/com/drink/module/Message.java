package com.drink.module;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ThinkPad on 2014/10/29.
 */
public class Message {

    public JSONObject getResult(boolean success,boolean show,Object returnObject,String errorMessages,String errorCode){
        JSONObject result=new JSONObject();
        JSONObject data=new JSONObject();
        data.put("success",success);
        data.put("show",show);
        if(success){
            data.put("returnObject",returnObject);
        }else{
            data.put("errorMessages",errorMessages);
            data.put("errorCode",errorCode);
        }
        result.put("result",data);
        return result;
    }

}
