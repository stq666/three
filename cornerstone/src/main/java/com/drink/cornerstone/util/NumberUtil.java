package com.drink.cornerstone.util;

import java.util.regex.Pattern;

/**
 * Created by jh on 14-6-6.
 */
public class NumberUtil {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    public static Integer getIndexNumberFormat(Long indexNumber){
        if(indexNumber==null){
            indexNumber=new Long(0);
        }else{
            indexNumber++;
        }
        return indexNumber.intValue();
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
