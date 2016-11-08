package com.drink.processdata;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 * 迁移数据的处理类
 * Created by xm on 2015/12/10.
 */
public class ProcessMysqlData implements Serializable{

    public static void main(String[]args){
        /***********主库开始**************/
        //1.处理公司表
        processCompanyTable();
        //2.财务（购买记录）表
        processFinanceTable();
        //3.处理厚通体力表
        processHt_scorea_ruleTable();
        //4.处理厚通认可币表
        processHt_scoreb_ruleTable();
        //5.处理app版本记录记录表
        processSystem_codeTable();
        //6.处理用户表
        processUserTable();
        /***********主库结束**************/
        /***********子库开始**************/
        //7.处理附件表
        processAttachmentTable();
        //8.处理关注表
        processAttentionTable();
        //9.基础数据表
        processBasicdataTable();
        //10.基础数据子表
        processBasicdatasubTable();
        //11.祝福语
        processBlessTable();
        //12.员工关怀
        processCareTable();
        //13.认可维度
        processDimensionTable();
        //14.认可行为清单
        processDimension_behaviorTable();
        //15.认可条件
        processDimension_requirementTable();
        //16.臭鸡蛋
        processEggTable();
        //17.求助表
        processHelpTable();
        //18.求助人员关系表
        processHelp_memberTable();
        //19.节目清单表
        processHolidayTable();
        //20.员工表
        processMemberTable();
        //21.通知表
        processNoticeTable();
        //22.绩效表
        processPerformanceTable();
        //23.绩效看板表
//        processPerseeTable();
        //24.表扬表
        processPraiseTable();
        //25.认可币币兑换礼物表
        processRcoin_exchangeTable();
        //26.认可币使用清单表
        processRcoin_productTable();
        //27.认可币记录表
        processRcoin_scoreTable();
        //28.动态情况表
        processRec_dynamic_conditionTable();
        //29.部门表
        processRec_org_organizationTable();
        //30.员工认可关系表
        processRecognise_memberTable();
        //31.红包账户管理
        processRedpackageTable();
        //32.评论表
        processReviewTable();
        //33.奖赏表
        processRewardTable();
        //34.奖赏事件
        processReward_actionTable();
        //35.奖赏催办
        processReward_hastenTable();
        //36.奖赏举报
        processReward_reportTable();
        //37.奖赏人员关系表
        processRewardaction_memberTable();
        //38.规则范围表
        processRulesrangeTable();
        //39.规则人员关系表
        processRulesrange_memberTable();
        //40.积分a获取记录
        processScorea_recordTable();
        //41.积分a规则表
        processScorea_ruleTable();
        //42.积分b兑换表达式表
        processScoreb_expressionTable();
        //43.认可币规则表S
        processScoreb_ruleTable();
        //44.
        processtarTable();
        /***********子库结束**************/

        /****************处理null的问题开始**********************/
        //45.修改null的问题
        updateNullFinance();
        //46.修改null的问题
        updateNullTt_scorea_rule();
        //47:system_code
        updateNullSystem_code();
        //48:user
        updateNullUser();
        //49:basicdata
        updateNullBasicdata();
        //50.basicdatasub
        updateNullbasicdatasub();
        //51.dimension
        updateNulldimension();
        //52.egg
        updateNullegg();
        //53.help
        updateNullhelp();
        //54.holiday
        updateNullholiday();
        //55.member
        updateNullmember();
        //56.performance
        updateNullperformance();
        //57.persee
        updateNullpersee();
        //58.praise
        updateNullpraise();
        //59.rcoin_exchange
        updateNullrcoin_exchange();
        //60.rcoin_score
        updateNullrcoin_score();
        //61.rec_dynamic_condition
        updateNullrec_dynamic_condition();
        //62.rec_org_organization
        updateNullrec_org_organization();
        //63.recognise_member
        updateNullrecognise_member();
        //64.review
        updateNullreview();
        //65.reward_action
        updateNullreward_action();
        //66.reward_hasten
        updateNullreward_hasten();
        //67.reward_report
        updateNullreward_report();
        //68.rewardaction_member
        updateNullrewardaction_member();
        //69.rulesrange
        updateNullrulesrange();
        //70.scorea_record
        updateNullscorea_record();
        //71.scorea_rule
        updateNullscorea_rule();

        /****************处理null的问题结束**********************/
        /****************处理公司生成部门的问题结束**********************/
        //72.处理公司生成部门的问题
        processCompanyToOrg();
        //73.更新部门的pid为公司的主键
        updateOrgPid();
        //74.更新总经理的部门到公司下
        updateOrgidToCompany();

        /****************处理公司生成部门的问题结束**********************/

    }

    /**
     * 74.更新总经理的部门到公司下
     */
    private static void updateOrgidToCompany() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from member where orgid=0", (byte) 3);
            int count = 0;
            while (rs.next()){
                Long companyid = rs.getLong("companyid");
                Long orgid=queryOrgid(companyid);
                String sql = "update member set orgid="+orgid+" where companyid="+companyid+" and orgid=0";
                count+= MysqlDriverUtil.insertData(sql, (byte)3);
            }
            System.out.println("74:member表成功更新"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Long queryOrgid(Long companyid) {
        Long id = null;
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rec_org_organization where pid=-1 and companyid="+companyid, (byte) 3);
            while (rs.next()){
              id = rs.getLong("id");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 73.更新部门的pid为公司的主键
     */
    private static void updateOrgPid() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rec_org_organization where department=1", (byte) 3);
            int count = 0;
            while (rs.next()){
                Long pid = rs.getLong("id");
                Long companyid = rs.getLong("companyid");
                String sql = "update rec_org_organization set pid="+pid+" where department=0 and companyid="+companyid;
                count+= MysqlDriverUtil.insertData(sql, (byte)3);
            }
            System.out.println("73:rec_org_organization表成功更新"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 72.处理公司生成部门的问题
     */
    private static void processCompanyToOrg() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from company", (byte) 0);
            int count = 0;
            Long id = getMaxId();
            while (rs.next()){
                id = id.longValue();
                ++id;
                Long companyid = rs.getLong("id");
                String name = rs.getString("name");
                String operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                String sql = "insert into rec_org_organization" +
                        " (id,name,pid,operatetime,companyid,status,department) " +
                        " values " +
                        "("+id+",'"+name+"',-1,'"+operatetime+"',"+companyid+",0,1)";
                count+= MysqlDriverUtil.insertData(sql, (byte)3);
            }
            System.out.println("72:rec_org_organization表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 获取部门的最大主键
     * @return
     */
    private static Long getMaxId() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select max(id) as maxid from rec_org_organization", (byte) 3);
            while(rs.next()){
                return rs.getLong("maxid");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 1.处理公司表
     */
    private static void processCompanyTable(){
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from company", (byte) 0);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Byte status = rs.getByte("status");
                Integer acountmount = rs.getInt("acountmount");
                String begindate = "0000-00-00 00:00:00";
                if(rs.getDate("begindate")!=null){
                    begindate = String.valueOf(rs.getDate("begindate"));
                }
                String enddate = "0000-00-00 00:00:00";
                if(rs.getDate("enddate")!=null){
                    enddate = String.valueOf(rs.getDate("enddate"));
                }
                String adminname = rs.getString("adminname");
                String password = rs.getString("password");
                String trade = rs.getString("trade");
                String property = rs.getString("property");
                Byte scale = rs.getByte("scale");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                String db = rs.getString("db");
                String server = rs.getString("server");
                String connectname = rs.getString("connectname");
                String occname = rs.getString("occname");
                String connectemail = rs.getString("connectemail");
                String connecttel = rs.getString("connecttel");
                String sql = "insert into company (id,name,status,acountmount,begindate,enddate,adminname,password,trade,property,scale,operatetime,db,server,connectname,occname,connectemail,connecttel) " +
                        " values ("+id+",'"+name+"',"+status+","+acountmount+",'"+begindate+"','"+enddate+"','"+adminname+"','"+password+"','"+trade+"','"+property+"',"+scale+",'"+operatetime+"','"+db+"','"+server+"','"+connectname+"','"+occname+"','"+connectemail+"','"+connecttel+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 2);
            }
            System.out.println("1:company表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 2.财务（购买记录）表
     */
    private static void processFinanceTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from finance", (byte) 0);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String companyname = rs.getString("companyname");
                Integer acountmount = rs.getInt("acountmount");
                Byte years = rs.getByte("years");
                Byte type = rs.getByte("type");
                String note = rs.getString("note");
                Double money = rs.getDouble("money");
                String openname = rs.getString("openname");
                String openbank = rs.getString("openbank");
                String number = rs.getString("number");
                Byte invoice = rs.getByte("invoice");
                String invoicetitle = rs.getString("invoicetitle");
                String invoicecomment = rs.getString("invoicecomment");
                String connectname = rs.getString("connectname");
                String connecttel = rs.getString("connecttel");
                Byte status = rs.getByte("status");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Byte contract = rs.getByte("contract");
                String sql = "insert into finance " +
                        "(id,companyname,acountmount,years,type,note,money,openname,openbank,number,invoice,invoicetitle,invoicecomment,connectname,connecttel,status,operatetime,contract) " +
                        "values" +
                        "("+id+",'"+companyname+"',"+acountmount+","+years+","+type+",'"+note+"',"+money+",'"+openname+"','"+openbank+"','"+number+"',"+invoice+",'"+invoicetitle+"','"+invoicecomment+"','"+connectname+"','"+connecttel+"',"+status+",'"+operatetime+"',"+contract+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 2);
            }
            System.out.println("2:finance表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 3.处理厚通体力表
     */
    private static void processHt_scorea_ruleTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from ht_scorea_rule", (byte) 0);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Integer begindate = rs.getInt("begindate");
                Integer beginday = rs.getInt("beginday");
                Integer score = rs.getInt("score");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Byte status = rs.getByte("status");
                Integer endday = rs.getInt("endday");
                String beginname = rs.getString("beginname");
                String endname = rs.getString("endname");
                Integer times = rs.getInt("times");
                String sql = "insert into ht_scorea_rule " +
                        "(id,begindate,beginday,score,operatetime,status,endday,beginname,endname,times) " +
                        "values" +
                        "("+id+","+begindate+","+beginday+","+score+",'"+operatetime+"',"+status+","+endday+",'"+beginname+"','"+endname+"',"+times+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 2);
            }
            System.out.println("3:ht_scorea_rule表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 4.处理厚通认可币表
     */
    private static void processHt_scoreb_ruleTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from ht_scoreb_rule", (byte) 0);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Double score = rs.getDouble("score");
                String operatetime = "0000-00-00 00:00:00";
                Byte status = rs.getByte("status");
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                String name = rs.getString("name");
                String sql = "insert into ht_scoreb_rule " +
                        "(id,score,operatetime,status,name) " +
                        "values" +
                        "("+id+","+score+",'"+operatetime+"',"+status+",'"+name+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 2);
            }
            System.out.println("4:ht_scoreb_rule表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 5.处理app版本记录记录表
     */
    private static void processSystem_codeTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from system_code", (byte) 0);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String code = rs.getString("code");
                String value = rs.getString("value");
                String downaddress = rs.getString("downaddress");
                String comment = rs.getString("comment");
                Byte type = rs.getByte("type");
                String sql = "insert into system_code " +
                        "(id,code,value,downaddress,comment,type) " +
                        "values" +
                        "("+id+",'"+code+"','"+value+"','"+downaddress+"','"+comment+"',"+type+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 2);
            }
            System.out.println("5:system_code表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 6.处理用户表
     */
    private static void processUserTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from user", (byte) 0);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String email = rs.getString("email");
                String name = rs.getString("name");
                String pwd = rs.getString("pwd");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String phone = rs.getString("phone");
                Byte ifmanager = rs.getByte("ifmanager");
                String sql = "insert into user " +
                        "(id,email,name,pwd,operatetime,companyid,phone,ifmanager,status) " +
                        "values" +
                        "("+id+",'"+email+"','"+name+"','"+pwd+"','"+operatetime+"',"+companyid+",'"+phone+"',"+ifmanager+",0)";
                count+= MysqlDriverUtil.insertData(sql, (byte) 2);
            }
            System.out.println("6:user表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 7.处理附件表
     */
    private static void processAttachmentTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from attachment", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String fileid = rs.getString("fileid");
                Long relationid = rs.getLong("relationid");
                Long operator = rs.getLong("operator");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String filename = rs.getString("filename");
                Byte type = rs.getByte("type");
                Long mid = rs.getLong("mid");
                String sql = "insert into attachment " +
                        "(id,fileid,relationid,operator,operatetime,companyid,filename,type,mid) " +
                        "values" +
                        "("+id+",'"+fileid+"',"+relationid+","+operator+",'"+operatetime+"',"+companyid+",'"+filename+"',"+type+","+mid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("7:attachment表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 8.处理关注表
     */
    private static void processAttentionTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from attention", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long fuid = rs.getLong("fuid");
                Long tuid = rs.getLong("tuid");
                Byte status = rs.getByte("status");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte type = rs.getByte("type");
                Long ptid = rs.getLong("ptid");
                String sql = "insert into attention " +
                        "(id,fuid,tuid,status,operatetime,companyid,type,ptid) " +
                        "values" +
                        "("+id+","+fuid+","+tuid+","+status+",'"+operatetime+"',"+companyid+","+type+","+ptid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("8:attention表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 9.基础数据表
     */
    private static void processBasicdataTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from basicdata", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Long subid = rs.getLong("subid");
                String comment = rs.getString("comment");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Long operator = rs.getLong("operator");
                String value = rs.getString("value");
                String sql = "insert into basicdata " +
                        "(id,name,subid,comment,operatetime,companyid,operator,value) " +
                        "values" +
                        "("+id+",'"+name+"',"+subid+",'"+comment+"','"+operatetime+"',"+companyid+","+operator+",'"+value+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("9:basicdata表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 10.基础数据子表
     */
    private static void processBasicdatasubTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from basicdatasub", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String comment = rs.getString("comment");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Long bid = rs.getLong("bid");
                String sql = "insert into basicdatasub " +
                        "(id,name,comment,operatetime,companyid,bid) " +
                        "values" +
                        "("+id+",'"+name+"','"+comment+"','"+operatetime+"',"+companyid+","+bid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("10:basicdatasub表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 11.祝福语
     */
    private static void processBlessTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from bless", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long operator = rs.getLong("operator");
                Long companyid = rs.getLong("companyid");
                Long careid = rs.getLong("careid");
                Byte type = rs.getByte("type");
                String sql = "insert into bless " +
                        "(id,name,operatetime,operator,companyid,careid,type) " +
                        "values" +
                        "("+id+",'"+name+"','"+operatetime+"',"+operator+","+companyid+","+careid+","+type+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("11:bless表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 12.员工关怀
     */
    private static void processCareTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from care", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Byte afternoon = rs.getByte("afternoon");
                Integer wtime = rs.getInt("wtime");
                Integer ftime = rs.getInt("ftime");
                Integer days = rs.getInt("days");
                Byte redb = rs.getByte("redb");
                Byte type = rs.getByte("type");
                Long bless = rs.getLong("bless");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long operator = rs.getLong("operator");
                Long companyid = rs.getLong("companyid");
                Byte remberoc = rs.getByte("remberoc");
                String sql = "insert into care " +
                        "(id,afternoon,wtime,ftime,days,redb,type,bless,operator,operatetime,companyid,remberoc) " +
                        "values" +
                        "("+id+","+afternoon+","+wtime+","+ftime+","+days+","+redb+","+type+","+bless+","+operator+",'"+operatetime+"',"+companyid+","+remberoc+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("12:care表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 13.认可维度
     */
    private static void processDimensionTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from dimension", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Byte status = rs.getByte("status");
                Byte category = rs.getByte("category");
                String comment = rs.getString("comment");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte type = rs.getByte("type");
                String sql = "insert into dimension " +
                        "(id,name,status,category,comment,operatetime,companyid,type) " +
                        "values" +
                        "("+id+",'"+name+"',"+status+","+category+",'"+comment+"','"+operatetime+"',"+companyid+","+type+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("13:dimension表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 14.认可行为清单
     */
    private static void processDimension_behaviorTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from dimension_behavior", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String serialnumber = rs.getString("serialnumber");
                String name = rs.getString("name");
                Byte type = rs.getByte("type");
                Byte status = rs.getByte("status");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");

                String sql = "insert into dimension_behavior " +
                        "(id,serialnumber,name,type,operatetime,companyid,status) " +
                        "values" +
                        "("+id+",'"+serialnumber+"','"+name+"',"+type+",'"+operatetime+"',"+companyid+","+status+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("14:dimension_behavior表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 15.认可条件
     */
    private static void processDimension_requirementTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from dimension_requirement", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long did = rs.getLong("did");
                String name = rs.getString("name");
                Integer score = rs.getInt("score");
                Byte type = rs.getByte("type");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");

                String sql = "insert into dimension_requirement " +
                        "(id,did,name,score,operatetime,companyid,type) " +
                        "values" +
                        "("+id+","+did+",'"+name+"',"+score+",'"+operatetime+"',"+companyid+","+type+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("15:dimension_requirement表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 16.臭鸡蛋
     */
    private static void processEggTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from egg", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long senderid = rs.getLong("senderid");
                String sendername = rs.getString("sendername");
                String senderdepname = rs.getString("senderdepname");
                Long receiverid = rs.getLong("receiverid");
                Long companyid = rs.getLong("companyid");
                String receivername = rs.getString("receivername");
                String receiverdepname = rs.getString("receiverdepname");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Byte status = rs.getByte("status");
                Long receivertwoid = rs.getLong("receivertwoid");
                String receivertwoname = rs.getString("receivertwoname");
                String receivertwodepname = rs.getString("receivertwodepname");
                Long eid = rs.getLong("eid");
                String content = rs.getString("content");
                String sql = "insert into egg " +
                        "(id,senderid,sendername,senderdepname,receiverid,receivername,receiverdepname,operatetime,companyid,status,receivertwoid,receivertwoname,receivertwodepname,eid,content) " +
                        "values" +
                        "("+id+","+senderid+",'"+sendername+"','"+senderdepname+"',"+receiverid+",'"+receivername+"','"+receiverdepname+"','"+operatetime+"',"+companyid+","+status+","+receivertwoid+",'"+receivertwoname+"','"+receivertwodepname+"',"+eid+",'"+content+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("16:egg表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 17.求助表
     */
    private static void processHelpTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from help", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long mid = rs.getLong("mid");
                String mname = rs.getString("mname");
                String content = rs.getString("content");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String name = rs.getString("name");
                String restrictcontent = rs.getString("restrictcontent");
                Byte status = rs.getByte("status");
                String sql = "insert into help " +
                        "(id,mid,mname,content,operatetime,companyid,name,restrictcontent,status) " +
                        "values" +
                        "("+id+","+mid+",'"+mname+"','"+content+"','"+operatetime+"',"+companyid+",'"+name+"','"+restrictcontent+"',"+status+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("17:help表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 18.求助人员关系表
     */
    private static void processHelp_memberTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from help_member", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long helpeid = rs.getLong("helpeid");
                Long helperid = rs.getLong("helperid");
                String helpername = rs.getString("helpername");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String sql = "insert into help_member " +
                        "(id,helpeid,helperid,helpername,operatetime,companyid) " +
                        "values" +
                        "("+id+","+helpeid+","+helperid+",'"+helpername+"','"+operatetime+"',"+companyid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("18:help_member表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 19.节目清单表
     */
    private static void processHolidayTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from holiday", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String day = "0000-00-00";
                if(rs.getDate("day")!=null){
                    day = String.valueOf(rs.getDate("day"));
                }
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long operator = rs.getLong("operator");
                Long companyid = rs.getLong("companyid");
                String comment = rs.getString("comment");
                Long blessid = rs.getLong("blessid");
                String sql = "insert into holiday " +
                        "(id,name,day,operatetime,operator,companyid,comment,blessid) " +
                        "values" +
                        "("+id+",'"+name+"','"+day+"','"+operatetime+"',"+operator+","+companyid+",'"+comment+"',"+blessid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("19:holiday表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 20.员工表
     */
    private static void processMemberTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from member", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String serialnumber = rs.getString("serialnumber");
                String name = rs.getString("name");
                Long orgid = rs.getLong("orgid");
                String occupation = rs.getString("occupation");
                String occupationlevel = rs.getString("occupationlevel");
                String entrydate = "0000-00-00 00:00:00";
                if(rs.getDate("entrydate")!=null){
                    entrydate = String.valueOf(rs.getDate("entrydate"));
                }
                String birthday = "0000-00-00 00:00:00";
                if(rs.getDate("birthday")!=null){
                    birthday = String.valueOf(rs.getDate("birthday"));
                }
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Byte authority = rs.getByte("authority");
                String portrait = rs.getString("portrait");
                Byte sex = rs.getByte("sex");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String pwd = rs.getString("pwd");
                Integer scorea = rs.getInt("scorea");
                Double rcoin = rs.getDouble("rcoin");
                Double rcoinfreeze = rs.getDouble("rcoinfreeze");
                Byte priod = rs.getByte("priod");
                String hxid = rs.getString("hxid");
                Double money = rs.getDouble("money");
                Long userid = rs.getLong("userid");
                Byte delstatus = rs.getByte("delstatus");
                String sql = "insert into member " +
                        "(id,serialnumber,name,orgid,occupation,occupationlevel,entrydate,email,phone,birthday,authority,portrait,sex,operatetime,companyid,pwd,scorea,rcoin,rcoinfreeze,priod,hxid,money,userid,delstatus) " +
                        "values" +
                        "("+id+",'"+serialnumber+"','"+name+"',"+orgid+",'"+occupation+"','"+occupationlevel+"','"+entrydate+"','"+email+"','"+phone+"','"+birthday+"',"+authority+",'"+portrait+"',"+sex+",'"+operatetime+"',"+companyid+",'"+pwd+"',"+scorea+","+rcoin+","+rcoinfreeze+","+priod+",'"+hxid+"',"+money+","+userid+","+delstatus+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("20:member表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 21.通知表
     */
    private static void processNoticeTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from notice", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long receiverid = rs.getLong("receiverid");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String sql = "insert into notice " +
                        "(id,receiverid,title,content,operatetime,companyid) " +
                        "values" +
                        "("+id+","+receiverid+",'"+title+"','"+content+"','"+operatetime+"',"+companyid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("21:notice表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 22.绩效表
     */
    private static void processPerformanceTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from performance", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long charge = rs.getLong("charge");
                Long creator = rs.getLong("creator");
                Byte status = rs.getByte("status");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                String finishtime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("finishtime")!=null){
                    finishtime = String.valueOf(rs.getTimestamp("finishtime"));
                }
                Long operator = rs.getLong("operator");
                String name = rs.getString("name");
                String comment = rs.getString("comment");
                Long companyid = rs.getLong("companyid");
                Byte rewardtype = rs.getByte("rewardtype");
                String sql = "insert into performance " +
                        "(id,charge,creator,status,operatetime,operator,name,comment,finishtime,companyid,rewardtype) " +
                        "values" +
                        "("+id+","+charge+","+creator+","+status+",'"+operatetime+"',"+operator+",'"+name+"','"+comment+"','"+finishtime+"',"+companyid+","+rewardtype+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("22:performance表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 23.绩效看板表
     */
    private static void processPerseeTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from persee", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long memberid = rs.getLong("memberid");
                Long departid = rs.getLong("departid");
                String countflag = rs.getString("countflag");
                Long companyid = rs.getLong("companyid");
                Long upcontinue = rs.getLong("upcontinue");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                String comment = rs.getString("comment");

                String sql = "insert into persee " +
                        "(id,memberid,departid,countflag,companyid,upcontinue,operatetime,comment,status) " +
                        "values" +
                        "("+id+","+memberid+","+departid+",'"+countflag+"',"+companyid+","+upcontinue+",'"+operatetime+"','"+comment+"',1)";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("23:persee表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 24.表扬表
     */
    private static void processPraiseTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from praise", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long senderid = rs.getLong("senderid");
                String sendername = rs.getString("sendername");
                String senderdepname = rs.getString("senderdepname");
                Long eid = rs.getLong("eid");
                Byte type = rs.getByte("type");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Long receiverid = rs.getLong("receiverid");
                String sql = "insert into praise " +
                        "(id,senderid,sendername,senderdepname,eid,type,operatetime,companyid,receiverid) " +
                        "values" +
                        "("+id+","+senderid+",'"+sendername+"','"+senderdepname+"',"+eid+","+type+",'"+operatetime+"',"+companyid+","+receiverid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("24:praise表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 25.认可币币兑换礼物表
     */
    private static void processRcoin_exchangeTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rcoin_exchange", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long mid = rs.getLong("mid");
                String mname = rs.getString("mname");
                String depname = rs.getString("depname");
                Long rpid = rs.getLong("rpid");
                Byte status = rs.getByte("status");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String sql = "insert into rcoin_exchange " +
                        "(id,mid,mname,depname,rpid,status,operatetime,companyid) " +
                        "values" +
                        "("+id+","+mid+",'"+mname+"','"+depname+"',"+rpid+","+status+",'"+operatetime+"',"+companyid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("25:rcoin_exchange表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 26.认可币使用清单表
     */
    private static void processRcoin_productTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rcoin_product", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Integer rcoin = rs.getInt("rcoin");
                String operatetime = null;
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String sql = "insert into rcoin_product " +
                        "(id,name,rcoin,operatetime,companyid) " +
                        "values" +
                        "("+id+",'"+name+"',"+rcoin+",'"+operatetime+"',"+companyid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("26:rcoin_product表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 27.认可币记录表
     */
    private static void processRcoin_scoreTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rcoin_score", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long mid = rs.getLong("mid");
                String mname = rs.getString("mname");
                String depname = rs.getString("depname");
                Double coin =rs.getDouble("coin");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Long eid = rs.getLong("eid");
                String sql = "insert into rcoin_score " +
                        "(id,mid,mname,depname,coin,operatetime,companyid,eid) " +
                        "values" +
                        "("+id+","+mid+",'"+mname+"','"+depname+"',"+coin+",'"+operatetime+"',"+companyid+","+eid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("27:rcoin_score表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 28.动态情况表
     */
    private static void processRec_dynamic_conditionTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rec_dynamic_condition", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long receiverid = rs.getLong("receiverid");
                Long senderid = rs.getLong("senderid");
                Long sendorgid = rs.getLong("sendorgid");
                Byte type = rs.getByte("type");
                Long behaviour = rs.getLong("behaviour");
                String content = rs.getString("content");
                String other = rs.getString("other");
                Long eid = rs.getLong("eid");
                String name = rs.getString("name");
                Long ptid =rs.getLong("ptid");
                Byte dytype = rs.getByte("dytype");
                String ids = rs.getString("ids");
                Long operator = rs.getLong("operator");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte sstatus = rs.getByte("sstatus");
                Double money = rs.getDouble("money");
                String refusereason = rs.getString("refusereason");
                String sql = "insert into rec_dynamic_condition " +
                        "(id,receiverid,senderid,sendorgid,type,behaviour,content,other,eid,name,ptid,dytype,ids,companyid,operator,operatetime,sstatus,money,refusereason) " +
                        "values" +
                        "("+id+","+receiverid+","+senderid+","+sendorgid+","+type+","+behaviour+",'"+content+"','"+other+"',"+eid+",'"+name+"',"+ptid+","+dytype+",'"+ids+"',"+companyid+","+operator+",'"+operatetime+"',"+sstatus+","+money+",'"+refusereason+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("28:rec_dynamic_condition表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 29.部门表
     */
    private static void processRec_org_organizationTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rec_org_organization", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                Long pid = rs.getLong("pid");
                String address = rs.getString("address");
                String telephone = rs.getString("telephone");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Long leader = rs.getLong("leader");
                Byte status = rs.getByte("status");
                String sql = "insert into rec_org_organization " +
                        "(id,name,pid,address,telephone,operatetime,companyid,leader,status,department) " +
                        "values" +
                        "("+id+",'"+name+"',"+pid+",'"+address+"','"+telephone+"','"+operatetime+"',"+companyid+","+leader+","+status+",0)";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("29:rec_org_organization表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 30.员工认可关系表
     */
    private static void processRecognise_memberTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from recognise_member", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long receiverid = rs.getLong("receiverid");
                String receivername = rs.getString("receivername");
                Long senderid = rs.getLong("senderid");
                String sendername = rs.getString("sendername");
                Long did = rs.getLong("did");
                Long drid = rs.getLong("drid");
                Long verifier = rs.getLong("verifier");
                Byte status = rs.getByte("status");
                Byte type = rs.getByte("type");
                Long behaviour = rs.getLong("behaviour");
                String reason = rs.getString("reason");
                String attachfile = rs.getString("attachfile");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte recstatus = rs.getByte("recstatus");
                String other = rs.getString("other");
                Long eid = rs.getLong("eid");
                String sql = "insert into recognise_member " +
                        "(id,receiverid,receivername,senderid,sendername,did,drid,verifier,status,type,behaviour,reason,attachfile,operatetime,companyid,recstatus,other,eid) " +
                        "values" +
                        "("+id+","+receiverid+",'"+receivername+"',"+senderid+",'"+sendername+"',"+did+","+drid+","+verifier+","+status+","+type+","+behaviour+",'"+reason+"','"+attachfile+"','"+operatetime+"',"+companyid+","+recstatus+",'"+other+"',"+eid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("30:recognise_member表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 31.红包账户管理
     */
    private static void processRedpackageTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from redpackage", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long mid = rs.getLong("mid");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte status = rs.getByte("status");
                Double money = rs.getDouble("money");
                Long fmid = rs.getLong("fmid");
                Byte type = rs.getByte("type");
                Long eid = rs.getLong("eid");
                String sql = "insert into redpackage " +
                        "(id,mid,companyid,operatetime,status,money,fmid,type,eid) " +
                        "values" +
                        "("+id+","+mid+","+companyid+",'"+operatetime+"',"+status+","+money+","+fmid+","+type+","+eid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("31:redpackage表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 32.评论表
     */
    private static void processReviewTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from review", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long receiverid = rs.getLong("receiverid");
                String receivername = rs.getString("receivername");
                Long senderid = rs.getLong("senderid");
                String sendername = rs.getString("sendername");
                String reviewname = rs.getString("reviewname");

                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Byte type = rs.getByte("type");
                Long companyid = rs.getLong("companyid");
                Long eid = rs.getLong("eid");
                Long pid = rs.getLong("pid");
                Integer feelings = rs.getInt("feelings");
                Integer feelingsvalue = rs.getInt("feelingsvalue");
                String sql = "insert into review " +
                        "(id,receiverid,receivername,senderid,sendername,reviewname,operatetime,type,companyid,eid,pid,feelings,feelingsvalue) " +
                        "values" +
                        "("+id+","+receiverid+",'"+receivername+"',"+senderid+",'"+sendername+"','"+reviewname+"','"+operatetime+"',"+type+","+companyid+","+eid+","+pid+","+feelings+","+feelingsvalue+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("32:review表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 33.奖赏表
     */
    private static void processRewardTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from reward", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String serialnumber = rs.getString("serialnumber");
                String name = rs.getString("name");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte status = rs.getByte("status");
                Byte type = rs.getByte("type");
                String sql = "insert into reward " +
                        "(id,serialnumber,name,companyid,operatetime,status,type) " +
                        "values" +
                        "("+id+",'"+serialnumber+"','"+name+"',"+companyid+",'"+operatetime+"',"+status+","+type+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("33:reward表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 34.奖赏事件
    */
    private static void processReward_actionTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from reward_action", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String enddate = "0000-00-00";
                if(rs.getDate("enddate")!=null){
                    enddate = String.valueOf(rs.getDate("enddate"));
                }
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte status = rs.getByte("status");
                String comment = rs.getString("comment");
                Double redpaper = rs.getDouble("redpaper");
                Long reward = rs.getLong("reward");
                String limitcomment = rs.getString("limitcomment");
                String attach = rs.getString("attach");
                Long senderid = rs.getLong("senderid");
                String sendername = rs.getString("sendername");
                String sql = "insert into reward_action " +
                        "(id,name,companyid,enddate,operatetime,status,comment,redpaper,reward,limitcomment,attach,senderid,sendername) " +
                        "values" +
                        "("+id+",'"+name+"',"+companyid+",'"+enddate+"','"+operatetime+"',"+status+",'"+comment+"',"+redpaper+","+reward+",'"+limitcomment+"','"+attach+"',"+senderid+",'"+sendername+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("34:reward_action表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 35.奖赏催办
     */
    private static void processReward_hastenTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from reward_hasten", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long senderid = rs.getLong("senderid");
                String sendername = rs.getString("sendername");
                String senderdepname = rs.getString("senderdepname");
                Long receiverid = rs.getLong("receiverid");
                String receivername = rs.getString("receivername");
                String receiverdepname = rs.getString("receiverdepname");
                String comment = rs.getString("comment");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Long raid = rs.getLong("raid");
                String sql = "insert into reward_hasten " +
                        "(id,senderid,sendername,senderdepname,receiverid,receivername,receiverdepname,operatetime,comment,companyid,raid) " +
                        "values" +
                        "("+id+","+senderid+",'"+sendername+"','"+senderdepname+"',"+receiverid+",'"+receivername+"','"+receiverdepname+"','"+operatetime+"','"+comment+"',"+companyid+","+raid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("35:reward_hasten表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 36.奖赏举报
     */
    private static void processReward_reportTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from reward_report", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long senderid = rs.getLong("senderid");
                String sendername = rs.getString("sendername");
                String senderdepname = rs.getString("senderdepname");
                Long receiverid = rs.getLong("receiverid");
                String receivername = rs.getString("receivername");
                String receiverdepname = rs.getString("receiverdepname");
                Byte status = rs.getByte("status");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Long raid = rs.getLong("raid");
                String comment = rs.getString("comment");
                String sql = "insert into reward_report " +
                        "(id,senderid,sendername,senderdepname,receiverid,receivername,receiverdepname,status,operatetime,companyid,raid,comment) " +
                        "values" +
                        "("+id+","+senderid+",'"+sendername+"','"+senderdepname+"',"+receiverid+",'"+receivername+"','"+receiverdepname+"',"+status+",'"+operatetime+"',"+companyid+","+raid+",'"+comment+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("36:reward_report表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 37.奖赏人员关系表
     */
    private static void processRewardaction_memberTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rewardaction_member", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long mid = rs.getLong("mid");
                Long raid = rs.getLong("raid");
                String comment = rs.getString("comment");
                Byte status = rs.getByte("status");
                Byte forreward = rs.getByte("forreward");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String sql = "insert into rewardaction_member " +
                        "(id,mid,raid,comment,operatetime,companyid,status,forreward) " +
                        "values" +
                        "("+id+","+mid+","+raid+",'"+comment+"','"+operatetime+"',"+companyid+","+status+","+forreward+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("37:rewardaction_member表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 38.规则范围表
     */
    private static void processRulesrangeTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rulesrange", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String score = rs.getString("score");
                String operatetime = null;
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long operator = rs.getLong("operator");
                Long companyid = rs.getLong("companyid");
                Byte type = rs.getByte("type");
                Byte ifchoose = rs.getByte("ifchoose");
                Long careid = rs.getLong("careid");
                Long blessid = rs.getLong("blessid");
                String enddate = "0000-00-00 00:00:00";
                if(rs.getTimestamp("enddate")!=null){
                    enddate = String.valueOf(rs.getTimestamp("enddate"));
                }
                String sql = "insert into rulesrange " +
                        "(id,name,score,operatetime,operator,companyid,type,ifchoose,careid,blessid,enddate) " +
                        "values" +
                        "("+id+",'"+name+"','"+score+"','"+operatetime+"',"+operator+","+companyid+","+type+","+ifchoose+","+careid+","+blessid+",'"+enddate+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("38:rulesrange表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 39.规则人员关系表
     */
    private static void processRulesrange_memberTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from rulesrange_member", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long rulerangeid = rs.getLong("rulerangeid");
                Long memberid = rs.getLong("memberid");
                Long companyid = rs.getLong("companyid");
                String sql = "insert into rulesrange_member " +
                        "(id,rulerangeid,memberid,companyid) " +
                        "values" +
                        "("+id+","+rulerangeid+","+memberid+","+companyid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("39:rulesrange_member表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 40.积分a获取记录
     */
    private static void processScorea_recordTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from scorea_record", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long mid = rs.getLong("mid");
                String mname = rs.getString("mname");
                String depname = rs.getString("depname");
                Integer score = rs.getInt("score");
                Byte type = rs.getByte("type");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String sql = "insert into scorea_record " +
                        "(id,mid,mname,depname,score,type,operatetime,companyid) " +
                        "values" +
                        "("+id+","+mid+",'"+mname+"','"+depname+"',"+score+","+type+",'"+operatetime+"',"+companyid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("40:scorea_record表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 41.积分a规则表
     */
    private static void processScorea_ruleTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from scorea_rule", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Byte type = rs.getByte("type");
                Integer begindate = rs.getInt("begindate");
                Integer beginday = rs.getInt("beginday");
                Integer score = rs.getInt("score");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte status = rs.getByte("status");
                Integer endday = rs.getInt("endday");
                String beginname = rs.getString("beginname");
                String endname = rs.getString("endname");
                Integer times = rs.getInt("times");
                String sql = "insert into scorea_rule " +
                        "(id,type,begindate,beginday,score,operatetime,companyid,status,endday,beginname,endname,times) " +
                        "values" +
                        "("+id+","+type+","+begindate+","+beginday+","+score+",'"+operatetime+"',"+companyid+","+status+","+endday+",'"+beginname+"','"+endname+"',"+times+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("41:scorea_rule表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 42.积分b兑换表达式表
     */
    private static void processScoreb_expressionTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from scoreb_expression", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                String content = rs.getString("content");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                String sql = "insert into scoreb_expression " +
                        "(id,content,operatetime,companyid) " +
                        "values" +
                        "("+id+",'"+content+"','"+operatetime+"',"+companyid+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("42:scoreb_expression表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 43.认可币规则表S
     */
    private static void processScoreb_ruleTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from scoreb_rule", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Byte type = rs.getByte("type");
                Double score = rs.getDouble("score");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte status = rs.getByte("status");
                String name = rs.getString("name");
                String sql = "insert into scoreb_rule " +
                        "(id,type,score,operatetime,companyid,status,name) " +
                        "values" +
                        "("+id+","+type+","+score+",'"+operatetime+"',"+companyid+","+status+",'"+name+"')";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("43:scoreb_rule表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 44
     */
    private static void processtarTable() {
        try{
            ResultSet rs = MysqlDriverUtil.selectData("select * from star", (byte) 1);
            int count = 0;
            while (rs.next()){
                Long id = rs.getLong("id");
                Long mid = rs.getLong("mid");
                String mname = rs.getString("mname");
                String mdepname = rs.getString("mdepname");
                Byte type = rs.getByte("type");
                String operatetime = "0000-00-00 00:00:00";
                if(rs.getTimestamp("operatetime")!=null){
                    operatetime = String.valueOf(rs.getTimestamp("operatetime"));
                }
                Long companyid = rs.getLong("companyid");
                Byte category = rs.getByte("category");
                Integer reccount = rs.getInt("reccount");
                Integer comcount = rs.getInt("comcount");
                String sql = "insert into star " +
                        "(id,mid,mname,mdepname,type,operatetime,companyid,category,reccount,comcount) " +
                        "values" +
                        "("+id+","+mid+",'"+mname+"','"+mdepname+"',"+type+",'"+operatetime+"',"+companyid+","+category+","+reccount+","+comcount+")";
                count+= MysqlDriverUtil.insertData(sql, (byte) 3);
            }
            System.out.println("44:star表成功插入"+count+"条");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 45finance
     */
    private static void updateNullFinance() {
        String note = "update finance set note=null where note='null'";
        System.out.println("45:修改finance的note共"+ MysqlDriverUtil.insertData(note, (byte) 2)+"条");
        String openname = "update finance set openname=null where openname='null'";
        System.out.println("45:修改finance的openname共"+ MysqlDriverUtil.insertData(openname, (byte) 2)+"条");
        String openbank = "update finance set openbank=null where openbank='null'";
        System.out.println("45:修改finance的openbank共"+  MysqlDriverUtil.insertData(openbank, (byte) 2)+"条");
        String number = "update finance set number=null where number='null'";
        System.out.println("45:修改finance的number共"+  MysqlDriverUtil.insertData(number, (byte) 2)+"条");
        String invoicetitle = "update finance set invoicetitle=null where invoicetitle='null'";
        System.out.println("45:修改finance的invoicetitle共"+  MysqlDriverUtil.insertData(invoicetitle, (byte) 2)+"条");
        String connectname = "update finance set connectname=null where connectname='null'";
        System.out.println("45:修改finance的connectname共"+  MysqlDriverUtil.insertData(connectname, (byte) 2)+"条");
        String connecttel = "update finance set connecttel=null where connecttel='null'";
        System.out.println("45:修改finance的connecttel共"+   MysqlDriverUtil.insertData(connecttel, (byte) 2)+"条");
    }
    /**
     * 46ht_scorea_rule
     */
    private static void updateNullTt_scorea_rule() {
        String endname = "update ht_scorea_rule set endname=null where endname='null'";
        System.out.println("46:修改ht_scorea_rule的endname共"+   MysqlDriverUtil.insertData(endname, (byte) 2)+"条");
    }
    /**
     * 47system_code
     */
    private static void updateNullSystem_code() {
        String comment = "update system_code set comment=null where comment='null'";
        System.out.println("47:修改system_code的comment共"+ MysqlDriverUtil.insertData(comment, (byte) 2)+"条");
    }
    /**
     * 48user
     */
    private static void updateNullUser() {
        String phone = "update user set phone=null where phone='null'";
        System.out.println("45:修改user的phone共"+ MysqlDriverUtil.insertData(phone, (byte) 2)+"条");
    }
    /**
     * 49:basicdata
     */
    private static void updateNullBasicdata() {
        String comment = "update basicdata set comment=null where comment='null'";
        System.out.println("49:修改basicdata的comment共"+ MysqlDriverUtil.insertData(comment, (byte) 3)+"条");
        String value = "update basicdata set value=null where value='null'";
        System.out.println("49:修改basicdata的value共"+ MysqlDriverUtil.insertData(value, (byte) 3)+"条");
    }
    /**
     * 50:basicdatasub
     */
    private static void updateNullbasicdatasub() {
        String comment = "update basicdatasub set comment=null where comment='null'";
        System.out.println("50:修改basicdatasub的comment共"+ MysqlDriverUtil.insertData(comment, (byte) 3)+"条");
    }
    /**
     * 51.dimension
     */
    private static void updateNulldimension() {
        String comment = "update dimension set comment=null where comment='null'";
        System.out.println("51:修改dimension的comment共"+ MysqlDriverUtil.insertData(comment, (byte) 3)+"条");
    }

    /**
     * 52.egg
     */
    private static void updateNullegg() {
        String sendername = "update egg set sendername=null where sendername='null'";
        System.out.println("52:修改egg的sendername共"+ MysqlDriverUtil.insertData(sendername, (byte) 3)+"条");
        String senderdepname = "update egg set senderdepname=null where senderdepname='null'";
        System.out.println("52:修改egg的senderdepname共"+ MysqlDriverUtil.insertData(senderdepname, (byte) 3)+"条");
        String receivername = "update egg set receivername=null where receivername='null'";
        System.out.println("52:修改egg的receivername共"+ MysqlDriverUtil.insertData(receivername, (byte) 3)+"条");
        String receiverdepname = "update egg set receiverdepname=null where receiverdepname='null'";
        System.out.println("52:修改egg的receiverdepname共"+ MysqlDriverUtil.insertData(receiverdepname, (byte) 3)+"条");
        String receivertwoname = "update egg set receivertwoname=null where receivertwoname='null'";
        System.out.println("52:修改egg的receivertwoname共"+ MysqlDriverUtil.insertData(receivertwoname, (byte) 3)+"条");
        String receivertwodepname = "update egg set receivertwodepname=null where receivertwodepname='null'";
        System.out.println("52:修改egg的receivertwodepname共"+ MysqlDriverUtil.insertData(receivertwodepname, (byte)3)+"条");
    }
    /**
     * 53.help
     */
    private static void updateNullhelp() {
        String mname = "update help set mname=null where mname='null'";
        System.out.println("53:修改help的mname共"+ MysqlDriverUtil.insertData(mname, (byte) 3)+"条");
        String restrictcontent = "update help set restrictcontent=null where restrictcontent='null'";
        System.out.println("53:修改help的restrictcontent共"+ MysqlDriverUtil.insertData(restrictcontent, (byte)3)+"条");
    }
    /**
     * 54.holiday
     */
    private static void updateNullholiday() {
        String comment = "update holiday set comment=null where comment='null'";
        System.out.println("54:修改holiday的comment共"+ MysqlDriverUtil.insertData(comment, (byte)3)+"条");
    }
    /**
     * 55.member
     */
    private static void updateNullmember() {
        String occupationlevel = "update member set occupationlevel=null where occupationlevel='null'";
        System.out.println("55:修改member的occupationlevel共"+ MysqlDriverUtil.insertData(occupationlevel, (byte)3)+"条");
        String entrydate = "update member set entrydate=null where entrydate='0000-00-00' or entrydate='null'";
        System.out.println("55:修改member的entrydate共"+ MysqlDriverUtil.insertData(entrydate, (byte)3)+"条");
        String birthday = "update member set birthday=null where birthday='0000-00-00' or birthday='null'";
        System.out.println("55:修改member的birthday共"+ MysqlDriverUtil.insertData(birthday, (byte)3)+"条");
        String portrait = "update member set portrait=null where portrait='0000-00-00' or portrait='null'";
        System.out.println("55:修改member的portrait共"+ MysqlDriverUtil.insertData(portrait, (byte)3)+"条");
        String hxid = "update member set hxid=null where  hxid='null'";
        System.out.println("55:修改member的hxid共"+ MysqlDriverUtil.insertData(hxid, (byte)3)+"条");
    }
    /**
     * 56.performance
     */
    private static void updateNullperformance() {
        String comment = "update performance set comment=null where comment='null'";
        System.out.println("56:修改performance的comment共"+ MysqlDriverUtil.insertData(comment, (byte)3)+"条");
    }
    /**
     * 57.persee
     */
    private static void updateNullpersee() {
        String comment = "update persee set comment=null where comment='null'";
        System.out.println("57:修改persee的comment共"+ MysqlDriverUtil.insertData(comment, (byte)3)+"条");
    }
    /**
     * 58.praise
     */
    private static void updateNullpraise() {
        String senderdepname = "update praise set senderdepname=null where senderdepname='null'";
        System.out.println("58:修改praise的comment共"+ MysqlDriverUtil.insertData(senderdepname, (byte)3)+"条");
    }
    /**
     * 59.rcoin_exchange
     */
    private static void updateNullrcoin_exchange() {
        String depname = "update rcoin_exchange set depname=null where depname='null'";
        System.out.println("59:修改rcoin_exchange的depname共"+ MysqlDriverUtil.insertData(depname, (byte)3)+"条");
    }
    /**
     * 60.rcoin_score
     */
    private static void updateNullrcoin_score() {
        String mname = "update rcoin_score set mname=null where mname='null'";
        System.out.println("60:修改rcoin_score的mname共"+ MysqlDriverUtil.insertData(mname, (byte)3)+"条");
        String depname = "update rcoin_score set depname=null where depname='null'";
        System.out.println("60:修改rcoin_score的depname共"+ MysqlDriverUtil.insertData(depname, (byte)3)+"条");
    }
    /**
     * 61.rec_dynamic_condition
     */
    private static void updateNullrec_dynamic_condition() {
        String content = "update rec_dynamic_condition set content=null where content='null'";
        System.out.println("61:修改rec_dynamic_condition的content共"+ MysqlDriverUtil.insertData(content, (byte)3)+"条");
        String other = "update rec_dynamic_condition set other=null where other='null'";
        System.out.println("61:修改rec_dynamic_condition的other共"+ MysqlDriverUtil.insertData(other, (byte)3)+"条");
        String name = "update rec_dynamic_condition set name=null where name='null'";
        System.out.println("61:修改rec_dynamic_condition的name共"+ MysqlDriverUtil.insertData(name, (byte)3)+"条");
        String ids = "update rec_dynamic_condition set ids=null where ids='null'";
        System.out.println("61:修改rec_dynamic_condition的ids共"+ MysqlDriverUtil.insertData(ids, (byte)3)+"条");
        String refusereason = "update rec_dynamic_condition set refusereason=null where refusereason='null'";
        System.out.println("61:修改rec_dynamic_condition的refusereason共"+ MysqlDriverUtil.insertData(refusereason, (byte)3)+"条");
    }
    /**
     * 62.rec_org_organization
     */
    private static void updateNullrec_org_organization() {
        String address = "update rec_org_organization set address=null where address='null'";
        System.out.println("62:修改rec_org_organization的address共"+ MysqlDriverUtil.insertData(address, (byte)3)+"条");
        String telephone = "update rec_org_organization set telephone=null where telephone='null'";
        System.out.println("62:修改rec_org_organization的telephone共"+ MysqlDriverUtil.insertData(telephone, (byte)3)+"条");
    }

    /**
     * 63.recognise_member
     */
    private static void updateNullrecognise_member() {
        String receivername = "update recognise_member set receivername=null where receivername='null'";
        System.out.println("63:修改recognise_member的receivername共"+ MysqlDriverUtil.insertData(receivername, (byte)3)+"条");
        String sendername = "update recognise_member set sendername=null where sendername='null'";
        System.out.println("63:修改recognise_member的sendername共"+ MysqlDriverUtil.insertData(sendername, (byte)3)+"条");
        String reason = "update recognise_member set reason=null where reason='null'";
        System.out.println("63:修改recognise_member的reason共"+ MysqlDriverUtil.insertData(reason, (byte)3)+"条");
        String attachfile = "update recognise_member set attachfile=null where attachfile='null'";
        System.out.println("63:修改recognise_member的attachfile共"+ MysqlDriverUtil.insertData(attachfile, (byte)3)+"条");
        String other = "update recognise_member set other=null where other='null'";
        System.out.println("63:修改recognise_member的other共"+ MysqlDriverUtil.insertData(other, (byte)3)+"条");
    }
    /**
     * 64.review
     */
    private static void updateNullreview() {
        String receivername = "update review set receivername=null where receivername='null'";
        System.out.println("64:修改review的receivername共"+ MysqlDriverUtil.insertData(receivername, (byte)3)+"条");
        String sendername = "update review set sendername=null where sendername='null'";
        System.out.println("64:修改review的sendername共"+ MysqlDriverUtil.insertData(sendername, (byte)3)+"条");
        String reviewname = "update review set reviewname=null where reviewname='null'";
        System.out.println("64:修改review的reviewname共"+ MysqlDriverUtil.insertData(reviewname, (byte)3)+"条");
    }
    /**
     * 65.reward_action
     */
    private static void updateNullreward_action() {
        String enddate = "update reward_action set enddate=null where enddate='null' or enddate='0000-00-00'";
        System.out.println("65:修改reward_action的enddate共"+ MysqlDriverUtil.insertData(enddate, (byte)3)+"条");
        String limitcomment = "update reward_action set limitcomment=null where limitcomment='null'";
        System.out.println("65:修改reward_action的limitcomment共"+ MysqlDriverUtil.insertData(limitcomment, (byte)3)+"条");
        String attach = "update reward_action set attach=null where attach='null'";
        System.out.println("65:修改reward_action的attach共"+ MysqlDriverUtil.insertData(attach, (byte)3)+"条");
        String sendername = "update reward_action set sendername=null where sendername='null'";
        System.out.println("65:修改reward_action的sendername共"+ MysqlDriverUtil.insertData(sendername, (byte)3)+"条");
    }
    /**
     * 66.reward_hasten
     */
    private static void updateNullreward_hasten() {
        String senderdepname = "update reward_hasten set senderdepname=null where senderdepname='null'";
        System.out.println("66:修改reward_hasten的senderdepname共"+ MysqlDriverUtil.insertData(senderdepname, (byte)3)+"条");
        String receiverdepname = "update reward_hasten set receiverdepname=null where receiverdepname='null'";
        System.out.println("66:修改reward_hasten的receiverdepname共"+ MysqlDriverUtil.insertData(receiverdepname, (byte)3)+"条");
        String comment = "update reward_hasten set comment=null where comment='null'";
        System.out.println("66:修改reward_hasten的comment共"+ MysqlDriverUtil.insertData(comment, (byte)3)+"条");
    }
    /**
     * 67.reward_report
     */
    private static void updateNullreward_report() {
        String senderdepname = "update reward_report set senderdepname=null where senderdepname='null'";
        System.out.println("67:修改reward_report的sendername共"+ MysqlDriverUtil.insertData(senderdepname, (byte)3)+"条");
        String receiverdepname = "update reward_report set receiverdepname=null where receiverdepname='null'";
        System.out.println("67:修改reward_report的receiverdepname共"+ MysqlDriverUtil.insertData(receiverdepname, (byte)3)+"条");
        String comment = "update reward_report set comment=null where comment='null'";
        System.out.println("67:修改reward_report的comment共"+ MysqlDriverUtil.insertData(comment, (byte)3)+"条");
    }
    /**
     * 68.rewardaction_member
     */
    private static void updateNullrewardaction_member() {
        String comment = "update rewardaction_member set comment=null where comment='null'";
        System.out.println("68:修改rewardaction_member的comment共"+ MysqlDriverUtil.insertData(comment, (byte)3)+"条");
    }
    /**
     * 69.rulesrange
     */
    private static void updateNullrulesrange() {
        String enddate = "update rulesrange set enddate=null where enddate='null' or enddate='0000-00-00 00:00:00'";
        System.out.println("69:修改rulesrange的comment共"+ MysqlDriverUtil.insertData(enddate, (byte)3)+"条");
    }
    /**
     * 70.scorea_record
     */
    private static void updateNullscorea_record() {
        String depname = "update scorea_record set depname=null where depname='null' ";
        System.out.println("70:修改scorea_record的depname共"+ MysqlDriverUtil.insertData(depname, (byte)3)+"条");
    }
    /**
     * 71.scorea_rule
     */
    private static void updateNullscorea_rule() {
        String endname = "update scorea_rule set endname=null where endname='null' ";
        System.out.println("71:修改scorea_rule的endname共"+ MysqlDriverUtil.insertData(endname, (byte)3)+"条");
    }

}
