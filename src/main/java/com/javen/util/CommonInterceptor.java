package com.javen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Jay on 2017/6/27.
 */

/**
 * 自定义拦截器，使用cookie验证用户登录状态
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger= LoggerFactory.getLogger(CommonInterceptor.class);

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
        String requestUri = request.getRequestURI();
        logger.info("拦截器：requestUri:"+requestUri);
        if (requestUri.equals("/myshop/user/loginout")){
            return true;
        }
        if (requestUri.equals(config.FrontPageUrl) || requestUri.indexOf("indexpage")>0 || requestUri.indexOf("login")>0){
            logger.info("访问首页或login！");
            return true;
        }
        if (requestUri.indexOf("query")>0 || requestUri.indexOf("getOrders")>0 ){
            logger.info("访问query！");
            return true;
        }
        boolean flag=true;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            logger.info("cookies.length:"+cookies.length);
        }
        if (cookies!=null && cookies.length>0) {
            for (Cookie c : cookies) {
                logger.info(c.getName() + "--->" + c.getValue());
                if (c.getName().equals("sessionid")){
                    String sessionid=c.getValue();
                    HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
                    if (session==null){
                        flag=false;
                        break;
                    }
                    else {
                        return true;
                    }
                }
            }
            logger.info("cookie遍历结束！");
            flag=false;
        }
        else {
            logger.info("不存在cookie！");
            flag = false;
        }
        if (flag){
            return true;
        }
        else {
            logger.info("重定向到:" + config.FrontPageUrl);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
    }
}
