package com.javen.util;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Jay on 2017/6/27.
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
    public static final String LAST_PAGE = "com.alibaba.lastPage";

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
        System.out.println("拦截器：requestUri:"+requestUri);
        if (requestUri.equals(config.FrontPageUrl) || requestUri.indexOf("indexpage")>0 || requestUri.indexOf("login")>0){
            System.out.println("访问首页或login！");
            return true;
        }
        if (requestUri.indexOf("query")>0 || requestUri.indexOf("getOrdersBy")>0 ){
            System.out.println("访问query！");
            return true;
        }
        boolean flag=true;
        Cookie[] cookies = request.getCookies();
        System.out.println("cookies:"+cookies);
        if(cookies!=null){
            System.out.println("cookies.length:"+cookies.length);
        }
        if (cookies!=null && cookies.length>0) {
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
//                        flag=true;
//                        break;
                        return true;
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
            System.out.println("重定向到:" + config.FrontPageUrl);
            //告诉ajax需要重定向
            response.setHeader("REDIRECT", "REDIRECT");             //注意需要配置报头可访问
            response.setHeader("CONTENTPATH", config.FrontPageUrl);     //告诉ajax重定向的路径
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
//        return true;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
//    @Override
//    public void postHandle(HttpServletRequest request,
//                           HttpServletResponse response, Object handler,
//                           ModelAndView modelAndView) throws Exception {
//        if(modelAndView != null){  //加入当前时间
//            modelAndView.addObject("var", "测试postHandle");
//        }
//    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
//    @Override
//    public void afterCompletion(HttpServletRequest request,
//                                HttpServletResponse response, Object handler, Exception ex)
//            throws Exception {
//    }
}
