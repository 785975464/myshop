package com.javen.util;

import java.util.HashMap;

/**
 * Created by Jay on 2017/6/24.
 */
public class config {
//    public static String sessionID;
    public static HashMap sessionmap = new HashMap();       //仅用作保存session，<"sessionid",session>
    public static HashMap userip = new HashMap();           //仅用作保存user的访问IP，<"uid",userip>
    public static String FrontPageUrl = "http://192.168.1.105:8888/myshop";
//public static String FrontPageUrl = "http://192.168.0.141:8888/myshop";
//    public static String FrontPageUrl = "http://192.168.191.1:8888/myshop";

//    public static String FrontPageUrl = "http://www.baidu.com";
//    public static String FrontPageUrl = "http://bbs.csdn.net/topics/390953392";

//    public static int userID;
//    public static String orderinfo;
}
