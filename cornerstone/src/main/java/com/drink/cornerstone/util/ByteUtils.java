package com.drink.cornerstone.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jh on 14-6-6.
 */
public class ByteUtils {
    /**
     *
     * @param obj
     * @return
     */
    public static byte[] objectToByte(Object obj){
        byte[] bytes =null;
        try{
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
            bo.close();
            oo.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"转化异常，请核对！");
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static Map byteToMap(byte[] bytes){
        Map turnMap=new HashMap();
        try{
            Object obj=null;
            if(bytes!=null){
                ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(bi);
                obj = oi.readObject();
                bi.close();
                oi.close();
                turnMap=(Map)obj;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"转化异常，请核对");
            e.printStackTrace();
        }
        return turnMap;
    }

    public static Object byteToObj(byte[] bytes){
        Object obj = null;
        try{
            if(bytes!=null){
                ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(bi);
                obj = oi.readObject();
                bi.close();
                oi.close();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"转化异常，请核对");
            e.printStackTrace();
        }
        return obj;
    }

}
