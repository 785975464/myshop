package com.javen.util;

import com.javen.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Jay on 2017/6/27.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookies = request.getCookies();
//        System.out.println("cookies:"+cookies);
//        if(cookies!=null){
//            System.out.println("cookies.length:"+cookies.length);
//        }
//        if (cookies!=null && cookies.length>0) {
//            for (Cookie c : cookies) {
//                System.out.println(c.getName() + "--->" + c.getValue());
//                if (c.getName().equals("sessionid")) {
//                    String sessionid = c.getValue();
//                    HttpSession session = (HttpSession) config.sessionmap.get(sessionid);
//                    if (session == null) {
//                        System.out.println("session为空！");
////                        return false;
//                    } else {
//                        System.out.println("session不为空！");
////                        return true;
//                    }
//                }
//            }
//        }

//        if ("GET".equalsIgnoreCase(request.getMethod())) {
//            RequestUtil.saveRequest();
//        }
        String requestUri = request.getRequestURI();
//
        System.out.println("权限拦截器：requestUri:"+requestUri);

        boolean flag=true;
        Cookie[] cookies = request.getCookies();
        System.out.println("cookies:"+cookies);
        if(cookies==null){
            return false;
        }
        if (cookies.length>0) {
            for (Cookie c : cookies) {
                System.out.println(c.getName() + "--->" + c.getValue());
                if (c.getName().equals("sessionid")){
                    String sessionid=c.getValue();
                    HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
                    if (session==null){
                        System.out.println("session为空！");
                        flag=false;
                        break;
                    }
                    else {
                        System.out.println("session不为空！");
                        User user = (User)session.getAttribute("userinfo");
                        if (user==null){
                            flag=false;
                            break;
                        }
                        System.out.println("当前用户为:"+user.getUsername()+"用户角色为："+user.getRole());
                        if (user.getRole()!=0){
                            System.out.println("当前用户没有权限");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            return false;
                        }
                    }
                }
            }
            System.out.println("cookie遍历结束！");
            flag=false;
        }
        else {
            System.out.println("不存在cookie！");
            flag = false;
        }
        if (flag){
            return true;
        }
        else {
            return false;
        }
    }
}
