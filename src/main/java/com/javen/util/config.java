package com.javen.util;

import java.util.HashMap;

/**
 * Created by Jay on 2017/6/24.
 */

/**
 * 系统配置项及session，包括处理状态、session集合、用户访问IP集合，及前端页面地址
 */
public class config {
    public static String SUCCESS = "success";
    public static String ERROR = "error";
    public static String OK = "ok";
    public static String BAD = "bad";
    public static String NO = "no";
    public static String YES = "yes";
    public static String NONE = "none";
    public static HashMap sessionmap = new HashMap();       //仅用作保存session，<"sessionid",session>
    public static HashMap userip = new HashMap();           //仅用作保存user的访问IP，<"uid",userip>
    public static String FrontPageUrl = "http://192.168.1.105:8888/myshop";     //公寓wifi
//  public static String FrontPageUrl = "http://192.168.0.141:8888/myshop";
//  public static String FrontPageUrl = "http://192.168.191.1:8888/myshop";     //自己开的wifi
}
