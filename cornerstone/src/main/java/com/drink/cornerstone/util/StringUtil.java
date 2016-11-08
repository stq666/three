package com.drink.cornerstone.util;

/**
 * 字符串工具类
 * Created by Administrator on 2016/5/2 0002.
 */
public class StringUtil {
    /**
     * 判断字符串是否为空
     * @param str
     * @return   如果为空返回true，不为空返回false
     */
    public static boolean isNull(String str){
        return str==null || "".equals(str);
    }

    /**
     * 判断字符串不为空
     * @param str
     * @return   如果为空则返回false，不为空则返回true
     */
    public static boolean isNotNull(String str){
        return str!=null && !"".equals(str);
    }

}
