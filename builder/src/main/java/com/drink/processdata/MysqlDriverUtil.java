package com.drink.processdata;


//import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 数据库驱动和链接的工具类
 * Created by xm on 2015/12/10.
 */
public class MysqlDriverUtil {
    private static final String OLDMAINURL = "jdbc:mysql://localhost:3306/recmain?zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    private static final String OLDSUBURL = "jdbc:mysql://localhost:3306/recsub?zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
//    private static final String NEWMAINURL = "jdbc:mysql://192.168.0.121:3206/recmain?zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
//    private static final String NEWSUBURL = "jdbc:mysql://192.168.0.121:3206/recsub?zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    private static final String NEWMAINURL = "jdbc:mysql://localhost:3306/recmainnew?zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    private static final String NEWSUBURL = "jdbc:mysql://localhost:3306/recsubnew?zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";
    private static final String NEWUSER = "root";
    private static final String NEWPASSWORD = "123456";
//    private ComboPooledDataSource dataSource;
    /**
     * 获取老的主表连接
     * @return
     */
    public static Connection getOldMainConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(OLDMAINURL, USER, PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取老的子表连接
     * @return
     */
    public static Connection getOldSubConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(OLDSUBURL, USER, PASSWORD);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取新的主表连接
     * @return
     */
    public static Connection getNewMainConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(NEWMAINURL, NEWUSER, NEWPASSWORD);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取新的子表连接
     * @return
     */
    public static Connection getNewSubConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(NEWSUBURL, NEWUSER, NEWPASSWORD);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询
     * @param sql    sql语句
     * @param flag   标示，0：老的主库，1:老的子库，2:新的主库，3:新的子库
     * @return   返回符合条件的数据
     */
    public static ResultSet selectData(String sql,Byte flag){
        PreparedStatement ps = null;
        Connection oldMain = null;
        Connection oldSub = null;
        Connection newMain = null;
        Connection newSub = null;

        try {
            if(flag==(byte)0){//老的主库
                oldMain = getOldMainConnection();
                ps = oldMain.prepareStatement(sql);
            }else if(flag==(byte)1){//老的子库
                oldSub = getOldSubConnection();
                ps = oldSub.prepareStatement(sql);
            }else if(flag==(byte)2){//新的主库
                newMain = getNewMainConnection();
                ps = newMain.prepareStatement(sql);
            }else if(flag==(byte)3){//新的子库
                newSub = getNewSubConnection();
                ps = newSub.prepareStatement(sql);
            }
            return ps.executeQuery(sql);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
        }
        return null;
    }

    /**
     * 增、删、改
     * @param sql
     * @param flag
     * @return
     */
    public static int insertData(String sql,Byte flag){
        PreparedStatement ps = null;
        Connection oldMain = null;
        Connection oldSub = null;
        Connection newMain = null;
        Connection newSub = null;
        try {
            if(flag==(byte)0){//老的主库
                oldMain = getOldMainConnection();
                ps = oldMain.prepareStatement(sql);
            }else if(flag==(byte)1){//老的子库
                oldSub = getOldSubConnection();
                ps = oldSub.prepareStatement(sql);
            }else if(flag==(byte)2){//新的主库
                newMain = getNewMainConnection();
                ps = newMain.prepareStatement(sql);
            }else if(flag==(byte)3){//新的子库
                newSub = getNewSubConnection();
                ps = newSub.prepareStatement(sql);
            }
           return ps.executeUpdate();
        }catch(Exception e){
            System.out.println(sql);
            e.printStackTrace();
        }finally {
            try{
                if(flag==(byte)0){
                    oldMain.close();
                }else if(flag==(byte)1){
                    oldSub.close();
                }else if(flag==(byte)2){
                    newMain.close();
                }else{
                    newSub.close();
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return 0;
    }
}
