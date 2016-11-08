package com.drink.cornerstone.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunteng on 14-4-22.
 */
public class CellType {
    /**
     * 得到excel 表中的值
     *
     * @param hssfCell Excel中的每个格子、
     * @return Excel格子中的值
     */
    public static String getValue(HSSFCell hssfCell) {
        if(hssfCell != null){
            if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                //返回boolean 类型的值
                return String.valueOf(hssfCell.getBooleanCellValue());
            } else if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                //返回数值类型的值
                if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                    //这里得判断是否能够检测到时间
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dt = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
                    String strdate = dateformat.format(dt);
                    return strdate;
                }
                BigDecimal bd = new BigDecimal(hssfCell.getNumericCellValue());
                return bd.toPlainString();
            } else {
                // 返回字符串类型的值
                return String.valueOf(hssfCell.getStringCellValue()).trim();
            }
        }
        return null;

    }

    public static String getValue(XSSFCell xssfCell) {
        if(xssfCell != null){
            if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
                //返回boolean 类型的值
                return String.valueOf(xssfCell.getBooleanCellValue());
            } else if (xssfCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                //返回数值类型的值
                if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
                    //这里得判断是否能够检测到时间
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dt = HSSFDateUtil.getJavaDate(xssfCell.getNumericCellValue());
                    String strdate = dateformat.format(dt);
                    return strdate;
                }
                BigDecimal bd = new BigDecimal(xssfCell.getNumericCellValue());
                return bd.toPlainString();
            } else {
                // 返回字符串类型的值
                return String.valueOf(xssfCell.getStringCellValue()).trim();
            }
        } else {
            return null;
        }

    }

    public static String getDoubleValue(HSSFCell hssfCell) {

        if (hssfCell != null){
            Double d = hssfCell.getNumericCellValue();
            if (d != null) {
                DecimalFormat df = new DecimalFormat("#.000000");
                String result = df.format(d);
                return  result;
            }
            return null;
        }
        return null;

    }

    public static String getDoubleValue(XSSFCell xssfCell) {
        if(xssfCell != null){
            Double d = xssfCell.getNumericCellValue();
            if (d != null) {
                DecimalFormat df = new DecimalFormat("#.000000");
                String result = df.format(d);
                return  result;
            }
            return null;
        }
        return null;

    }

    public static Date formatDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //20140304
        String strDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
        Date newDate = null;
        try {
            newDate = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static boolean isExcel2003(String fileName) {
        return fileName.matches("^.+\\.(xls)$");
    }

    public static boolean isExcel2007(String fileName) {
        return fileName.matches("^.+\\.(xlsx)$");
    }



}
