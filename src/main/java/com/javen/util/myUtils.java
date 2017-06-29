package com.javen.util;

import com.javen.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Jay on 2017/6/29.
 */
public class myUtils {
    public static void printMsg(HttpServletRequest request, HttpServletResponse response, String msg) throws IOException {
        String callback = request.getParameter("callback");
        String jsonstring=callback+"({\"msg\":\""+msg+"\"})";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    public static void printObjectMsg(HttpServletRequest request, HttpServletResponse response, Object msg) throws IOException {
        String callback = request.getParameter("callback");
        String jsonstring=callback+"({\"msg\":"+msg+"})";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    public static User getCurrentLocalUser(HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();    //从用户的cookie中取出用户信息
        System.out.println("getCurrentLocalUser! cookies:"+cookies);
        User user=null;
        if (cookies!=null && cookies.length>0) {
            for (Cookie c : cookies) {
                if (c.getName().equals("sessionid")) {
                    String sessionid = c.getValue();
                    HttpSession session = (HttpSession) config.sessionmap.get(sessionid);
                    if (session == null) {
                        System.out.println("session为空！");
                    } else {
                        System.out.println("session不为空！");
//                        int id = (Integer) session.getAttribute("id");
//                        user = (User) userService.get(id);
                        user=(User)session.getAttribute("userinfo");
                    }
                    break;
                }
            }
        }
//        User user = (User) config.sessionmap.get("userinfo");
        if (user!=null) {
            System.out.println("当前user信息：" + user.getId() + " " + user.getUsername());
        }
        return user;
    }
}
