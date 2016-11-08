package com.drink.cornerstone.sequence;

import com.drink.cornerstone.constant.ConstantElement;
import com.drink.cornerstone.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ThinkPad on 2014/11/14.
 */
public class SerialnumberGenerator {

    public static String getMaxSerialnumber(int bit,String value){
        return String.format("%0"+bit+"d",Long.parseLong(value)+1);
    }

    public static String getMaxSerialnumberNew(int bit,String serialnumber){
        String dateStr = DateUtils.getDatestr(new Date(), new SimpleDateFormat("yyyyMMdd"));
        String serialnumbertemp="";
        if(serialnumber!=null){
            if(serialnumber.length()==11){
               if(serialnumber.substring(0,8).equals(dateStr)){
                   String num= serialnumber.substring(8,serialnumber.length());
                   serialnumbertemp=  dateStr+getMaxSerialnumber(bit,num);
               }else{
                   serialnumbertemp=dateStr+ ConstantElement.occ_codeNum;
               }
            }else{
                System.out.println(" 最大MaxSerialnumber不符合长度,加一失败 ");
            }
        }else{
            serialnumbertemp=dateStr+ ConstantElement.occ_codeNum;
        }
        return serialnumbertemp;
    }



    public static void main(String args[]){
//     System.out.print(SerialnumberGenerator.getMaxSerialnumberNew(2, "001"));
     System.out.print(SerialnumberGenerator.getMaxSerialnumber(2, "003"));

    }
}
