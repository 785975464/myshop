package com.javen.util;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jay on 2017/6/23.
 */
public class CrossFilter extends OncePerRequestFilter {     //允许跨域过滤器，高版本的SpringMVC更改为使用注解@CrossOrigin
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,IOException{
//        System.out.println("发现一次跨域访问！");
//        if (request.getHeader("Access-Control-Request-Method")!=null && "OPTIONS".equals(request.getMethod())){
            response.addHeader("Access-Control-Allow-Origin","*");
            response.addHeader("Access-Control-Allow-Headers","Content-type");
            response.addHeader("Access-Control-Max-Age","1800");
            response.addHeader("Access-Control-Allow-Credentials","true");
            response.addHeader("Access-Control-Expose-Headers","REDIRECT");     //允许前台跨域访问REDIRECT
            response.addHeader("Access-Control-Expose-Headers","CONTENTPATH");     //允许前台跨域访问CONTENTPATH
//            System.out.println("发现一次跨域访问！");
//        }
//        System.out.println("发现一次跨域访问！"+request.getRequestURI());
        filterChain.doFilter(request,response);
    }
}
