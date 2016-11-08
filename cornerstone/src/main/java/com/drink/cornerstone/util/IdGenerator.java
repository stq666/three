package com.drink.cornerstone.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ThinkPad on 14-3-11.
 */
public class IdGenerator {
    /**
     * 获取主键id
     * @return  主键
     */
    public static Long getId(){
        String id = "1";
        long lastTime=0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("",
                    Locale.SIMPLIFIED_CHINESE);
            sdf.applyPattern("yyyyMMddHHmmssSSS");
            Date date = new Date();
            if(lastTime ==0){
                lastTime = date.getTime();
            }else{
                if(lastTime > date.getTime() || lastTime == date.getTime()){
                    lastTime = lastTime + 1;
                    date.setTime(lastTime);
                }else{
                    lastTime = date.getTime();
                }
            }
            id= sdf.format(date);
        } catch (Exception e) {
            System.out.println("IdGenerator occur an error: " + e.toString());
        }
        return new Long(id.substring(2));
    }
}
