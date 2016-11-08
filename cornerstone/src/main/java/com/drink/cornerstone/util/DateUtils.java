package com.drink.cornerstone.util;


import org.springframework.util.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DateUtils {
    public static final int ADD = 1;
    public static final int MINUS = 2;
    public static Date getBusinessDate(Date srcDate, int type, int num){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(srcDate);
        Date businessDate = DateUtils.parse(dateStr, sdf);
        Calendar c = Calendar.getInstance();
        c.setTime(businessDate);
        if(type == 1){
            c.add(Calendar.DATE,num);
        }else if(type==2){
            c.add(Calendar.DATE,-num);
        }

        return c.getTime();
    }

	/**
	 * 
	 * @return
	 */
	public static Date currentDate(DateFormat format) {
		Date today = new Date();
		if (format != null) {
			String string = format.format(today);
			today = parse(string, format);
		}
		return today;
	}

	/* 将字符串转换成日期 */
	public static Date getDateByString(String rq) {
		DateFormat df = new SimpleDateFormat();
		Date d = null;
		try {
			d = df.parse(rq);
		} catch (Exception e) {
		}
		return d;
	}

	/**
	 * 比较时间是否相同
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean equals(Date start, Date end) {
		if (start != null && end != null && start.getTime() == end.getTime()) {
			return true;
		}
		return false;
	}

    /**
     *
     * @param aMask
     * @param strDate
     * @return
     */
	public static final Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		try {
			date = df.parse(strDate);
		} catch (Exception pe) {
			pe.printStackTrace();
		}
		return (date);
	}

	public static boolean isBetween(Date date, Date from, Date to) {
		Assert.notNull(date, "date cannot be null.");
		Assert.notNull(from, "from cannot be null.");
		Assert.notNull(to, "to cannot be null.");
		Assert.isTrue(!from.after(to), "from cannot be after to.");
		return !date.before(from) && !date.after(to);
	}

	public static Date ifNull(Date date, Date defaultDate) {
		return date != null ? date : defaultDate;
	}


	public static Date parse(String date, DateFormat df) {
		try {
			return df.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("parse date [" + date
					+ "] failed in use [" + getDayFormatter() + "]", e);
		}
	}

    public static String getDatestr(Date date,SimpleDateFormat format){
       return  format.format(date);
    }

	/**
	 * <li>SimpleDateFormat is not thread saft, so when you need, you should
	 * create it</li>
	 */
	public static SimpleDateFormat getDayFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	public static SimpleDateFormat getMinuteFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm");
	}

	public static SimpleDateFormat getMonthFormatter() {
		return new SimpleDateFormat("yyyy-MM");
	}

	public static SimpleDateFormat getSecondFormatter() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

    /**
     * 获取指定天数的下一个月
     * @param date
     * @return
     */
    public static Date getNextMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month=c.get(Calendar.MONTH);
        c.set(Calendar.MONTH,month+1);
        return c.getTime();
    }

    /**
     * 获取指定月的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE,1);//设为当前月的1 号
        return c.getTime();
    }
    /**
     * 获取指定月的最后一天
     * @param date
     * @return
     */
    public  static  Date getLastDayOfMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }
    /**
     * 获取每个季度的第一天
     * @param date
     * @return
     */
    public  static Date getFirstDayOfQuarter(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 0);
                c.set(Calendar.DATE, 1);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 3);
                c.set(Calendar.DATE, 1);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH,6);
                c.set(Calendar.DATE, 1);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 9);
                c.set(Calendar.DATE, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }
    /**
     * 获取每个季度的最后一天
     * @param date
     * @return
     */
    public  static Date getLastDayOfQuarter(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH,8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获取指定日期上半年或下半年的最后一天
     * @param date
     * @return
     */
    public  static  Date getLastDayOfHalfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获取指定日期上半年或下半年的第一天 TODO
     * @param date
     * @return
     */
    public  static  Date getFirstDayOfHalfYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 0);
                c.set(Calendar.DATE, 1);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 6);
                c.set(Calendar.DATE, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获取指定日期的年的最后一天
     * @param date
     * @return
     */
    public  static Date getLastDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获取指定日期的年的第一天
     * @param date
     * @return
     */
    public  static Date getFirstDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            c.set(Calendar.DAY_OF_YEAR, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 获取日期的年
     * @param date
     * @return
     */
    public static int getYear(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(c.YEAR);
    }
	public static void main(String[] args) throws ParseException {
//      System.out.print( DateUtils.getYear(new SimpleDateFormat("yyyy-MM-dd").parse("2014-01-01")));
//      System.out.print( DateUtils.getYear(new SimpleDateFormat("yyyy-MM-dd").parse("2014-05-01")));
//      System.out.print( DateUtils.getYear(new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01")));
//      System.out.print( DateUtils.getMonthEndTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01")));
      System.out.print( DateUtils.getFirstDayOfHalfYear(new SimpleDateFormat("yyyy-MM-dd").parse("2014-06-23")));
//        System.out.println(String.format("%.2f", Double.valueOf(0)).toString());

	}
}
