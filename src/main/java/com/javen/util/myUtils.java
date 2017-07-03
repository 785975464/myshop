package com.javen.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.model.User;
import org.apache.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Jay on 2017/6/29.
 * 工具类。主要用于向前端输出数据，以及获取当前登录用户信息
 */
public class myUtils {
    private static Logger logger=Logger.getLogger(myUtils.class);

    /**
     * 将查询结果msg以jsonp格式返回给前端
     * @param request
     * @param response
     * @param msg
     */
    public static void printMsg(HttpServletRequest request, HttpServletResponse response, String msg){
        try {
            String callback = request.getParameter("callback");
            String jsonstring=callback+"({\"msg\":\""+msg+"\"})";
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }catch (IOException io){
            logger.error(io.getMessage());
        }
    }

    /**
     * 将查询结果对象以json格式返回给前端
     * @param response
     * @param obj
     */
    public static void printQueryMsg(HttpServletResponse response, Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(obj));
            response.getWriter().close();
        }catch (IOException io){
            logger.error(io.getMessage());
        }
    }

    /**
     * 将查询结果对象以jsonp格式返回给前端
     * @param request
     * @param response
     * @param msg
     */
    public static void printObjectMsg(HttpServletRequest request, HttpServletResponse response, Object msg) {
        try {
            String callback = request.getParameter("callback");
            String jsonstring=callback+"({\"msg\":"+msg+"})";
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }catch (IOException io){
            logger.error(io.getMessage());
        }
    }

    /**
     * 将查询list对象以datatable的json格式返回给前端
     * @param response
     * @param list
     */
    public static void printListMsg(HttpServletResponse response, List list) {
        try {
            String listjson = JsonUtil.listToJson(list);
            String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+
                    list.size()+",\"recordsFiltered\":"+list.size()+"}";
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }catch (IOException io){
            logger.error(io.getMessage());
        }

    }

    /**
     * 通过cookie获取当前登录用户信息
     * @param request
     * @return user
     */
    public static User getCurrentLocalUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        logger.info("getCurrentLocalUser! cookies:"+cookies);
        User user=null;
        if (cookies!=null && cookies.length>0) {
            for (Cookie c : cookies) {
                if (c.getName().equals("sessionid")) {
                    String sessionid = c.getValue();
                    HttpSession session = (HttpSession) config.sessionmap.get(sessionid);
                    if (session != null) {
                        user=(User)session.getAttribute("userinfo");
                    }
                    break;
                }
            }
        }
        if (user!=null) {
            logger.info("当前user信息：" + user.getId() + " " + user.getUsername());
        }
        return user;
    }

    /**
     * 通过传递参数cookie获取当前登录用户信息（对于无法主动获取客户端cookie的情况，由客户端提供cookie）
     * @param request
     * @return
     */
    public static User getUserByCookie(HttpServletRequest request) {
        String sessionid = request.getParameter("sessionid");
        HttpSession session = (HttpSession) config.sessionmap.get(sessionid);
        User user = (User)session.getAttribute("userinfo");
        return user;
    }
}
