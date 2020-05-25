package com.company.utils;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;

/**
 * Json工具
 */
public class JsonUtils {

    public static String setJson(String from,String to,String MSG,String filepath,String ok){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("from",from);
        jsonObject.put("to",to);
        jsonObject.put("msg",MSG);
        jsonObject.put("time", Time.getTime());
        if(filepath!=null){
            jsonObject.put("file", filepath);
        }
        if(ok!=null){
            jsonObject.put("ok", ok);
        }
        //System.out.println("JsonObject:"+jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }

    public static String setJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileEND",null);
        return jsonObject.toJSONString();
    }


    public static String loginF(String username,int port,int filePort){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username",username);
        jsonObject.put("port",port+"");
        jsonObject.put("filePort",filePort+"");
        return jsonObject.toJSONString();
    }

    public static Map<String,String> getJson(String str){
        return (Map<String,String>)JSONObject.parse(str);
    }

}