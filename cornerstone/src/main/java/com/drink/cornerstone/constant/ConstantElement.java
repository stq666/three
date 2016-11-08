package com.drink.cornerstone.constant;

/**
 * Created by sunteng on 14-4-26.
 */
public interface ConstantElement {

    static final String _USERINFO = "_USERINFO";
    static final String _SERIALNUMBER = "_SERIALNUMBER";
    static final String SESSION_FORCE_LOGOUT_KEY = "forceLogout";
    static final String errorCommonCode = "401";
    static final String errorForbidCode = "403";
    static final int pageSize = 20;
    static final String errorPassord = "errorPassord";
    static final String securityUserError = "用户名或密码错误！";
    static final String securityUserNotFind = "用户名不存在！";
    static final String securityUserStatusError = "登陆状态失效！";
    static final String securityUserLogoutSuccess = "退出成功！";
    static final String commonError = "系统罢工了，请稍后再试！";
    static final String commonSuccess = "操作成功！";
    static final String password = "123456";
    static final  String existEmail = "该邮箱已存在！";
    static final  String existPhone = "该手机号已存在！";
    static final  String existCompany = "公司名称已存在！";
    static final  String existSerialnumber = "该编码已存在！";
    static final  String existSerialnumberOrg = "部门还存在员工";
    static final  String commonNameIsNull = "名称不能为空！";
    static final  String existOrgName = "该部门名称重复，请重新输入！";
    static final  String depNameIsNull = "部门名称不能为空！";
    static final  String occNameIsNull = "职位名称不能为空！";
    static final  String authorityIsNull = "权限不能为空！";
    static final  String phoneIsNull = "手机号不能为空！";
    static final  String emailIsNull = "邮箱不能为空！";
    static final  String LeaderIsNull = "公司负责人不能为空！";
    static final  String orgNameIsNull = "部门名不能为空！";
    static final  String companyNotDelete = "已有公司记录，不能删除！";
    static final  String commonSerialNumberIsNull = "编码不能为空！";
    static final  String rewardIsQuote = "激励行为已被引用，无法删除！";
    static final  String RcoinIsQuote = "该奖品已有兑换记录，无法删除！";
    static final  String ScoreLevelIsQuote = "积分等级已经应用，无法删除！";
    static final String occ_codeNum="001";
    static final String commonExistNum = "编码已经存在。";
    static final String emailPattern =  "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    static final String datePattern = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
    static final String existPersee = "此用户在此次已经考核过了，不能再考核了";
    static final String existManagerEmail = "管理员账号已存在";
    static final String existPManagerEmail = "总经理邮箱已存在";
    static final String existPManagerPhone = "总经理手机已存在";
    static final String companyDisable = "公司服务已经到期，请联系续费启用";

    public   String huanxin_access_token="";//环信的access_token
    static final String huanxin_TokenUrl="https://a1.easemob.com/houtongconsulting/vrec/token";//环信获取access_token的地址
    static final String huanxin_RegisterUrl="https://a1.easemob.com/houtongconsulting/vrec/users";//环信的注册地址
    static final String huanxin_UpdateNickUrl="https://a1.easemob.com/houtongconsulting/vrec/users/lhg";//环信的修改昵称地址
    static final String huanxin_UpdatePwdUrl="https://a1.easemob.com/houtongconsulting/vrec/users/lhg/password";//环信的修改密码地址
    static final String huanxin_AddUserUrl="https://a1.easemob.com/houtongconsulting/vrec/users/lhg/contacts/users/liu";//环信的添加好友地址
    static final String huanxin_grant_type="client_credentials";
    static final String huanxin_client_id="YXA6T070ULN4EeWjmUWncVlo8A";
    static final String huanxin_client_secret="YXA6ZrGPlcUMKmo4NoFtboXSLw8hYgY";

            ;
}
