package com.drink.cornerstone.util;

import java.util.Map;

/**
 * Created by kk on 14-4-4.
 */
public class MapUtils {

    public static final String START = "start";
    public static final String LIMIT = "limit";

    public static final String STARTDATE = "startdate";
    public static final String ENDDATE = "enddate";

    public static void initPageMap(Map map){
        if(map !=null)
        {
            if(map.containsKey(START))
                map.put(START, Integer.parseInt(map.get(START).toString()));
            if(map.containsKey(LIMIT))
                map.put(LIMIT, Integer.parseInt(map.get(LIMIT).toString()));

            if(map.containsKey(STARTDATE)&&map.get(STARTDATE)!=null&&!"".equals(map.get(STARTDATE).toString().trim()))
                map.put(STARTDATE, map.get(STARTDATE).toString().substring(0,10));
            if(map.containsKey(ENDDATE)&&map.get(ENDDATE)!=null&&!"".equals(map.get(ENDDATE).toString().trim()))
                map.put(ENDDATE, map.get(ENDDATE).toString().substring(0,10));

        }
    }

}
