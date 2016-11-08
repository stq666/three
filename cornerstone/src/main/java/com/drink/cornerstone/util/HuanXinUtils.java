package com.drink.cornerstone.util;

import com.drink.cornerstone.constant.ConstantElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by ThinkPad on 16-1-21.
 */
public class HuanXinUtils {
    static ThreadLocal<String> access_token = new ThreadLocal<>();
    public static Void getAccessToken(){

        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new HuanXinClient();
            httpPost = new HttpPost(ConstantElement.huanxin_TokenUrl);

            JSONObject param = new JSONObject();
            param.put("grant_type", ConstantElement.huanxin_grant_type);
            param.put("client_id", ConstantElement.huanxin_client_id);
            param.put("client_secret", ConstantElement.huanxin_client_secret);
// 绑定到请求 Entry
            StringEntity se = new StringEntity(param.toString());
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"UTF-8");
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println(jsonObject);
                    access_token.set(jsonObject.get("access_token").toString());
                }
            }
            System.out.println("----------result:--------"+result);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }




    public static boolean Register(String name,String pwd,String nickname){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new HuanXinClient();
            httpPost = new HttpPost(ConstantElement.huanxin_RegisterUrl);
            if( access_token.get()==null || access_token.get().equals("")){
                getAccessToken();
            }
            httpPost.addHeader("Authorization"," Bearer "+access_token.get());
            JSONObject param = new JSONObject();
            param.put("username", name.replaceAll("[^a-zA-Z_\u4e00-\u9fa5\0-9]", ""));
            param.put("password", pwd);
            param.put("nickname", nickname);
// 绑定到请求 Entry
            StringEntity se = new StringEntity(param.toString(),"utf-8");
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"utf-8");
                    JSONObject jsonObject = new JSONObject(result);
                    System.out.println(jsonObject);
                }
            }
            System.out.println("----------result:--------"+result);
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }




//

    public static void main(String[] org){
//        getAccessToken();//获取环信token
        Register("wjg1231234.com","123456","王建国1234");//注册用户
//        String str ="  [ abc/abc?u*t:ert<enter>open|cnp\"ftrd123 ]";
//        System.out.println(str.replaceAll("[^a-zA-Z_\u4e00-\u9fa5\0-9]", ""));
    }
}
