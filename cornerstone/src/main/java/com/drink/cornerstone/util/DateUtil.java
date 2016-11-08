package com.drink.cornerstone.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by xm on 2015/11/8.
 */
public class DateUtil implements Serializable {
    public static String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取当天的开始
     * @return
     */
    public static Date currentDayStart(){
        Calendar c1 = new GregorianCalendar();
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        return new Date(c1.getTimeInMillis());
    }

    /**
     * 获取当天的结束
     * @return
     */
    public static Date currentDayEnd(){
        Calendar c2 = new GregorianCalendar();
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 59);
        c2.set(Calendar.SECOND, 59);
        return new Date(c2.getTimeInMillis());
    }
    public static void main(String[] args) {

        System.out.println(currentDayStart());

        System.out.println(currentDayEnd());
    }
}
