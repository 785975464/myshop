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

/**
 * 允许跨域过滤器，高版本的SpringMVC更改为使用注解@CrossOrigin
 * 允许前台跨域访问REDIRECT
 * 允许前台跨域访问CONTENTPATH
 */
public class CrossFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException,IOException{
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Headers","Content-type");
        response.addHeader("Access-Control-Max-Age","1800");
        response.addHeader("Access-Control-Allow-Credentials","true");
        response.addHeader("Access-Control-Expose-Headers","REDIRECT");
        response.addHeader("Access-Control-Expose-Headers","CONTENTPATH");
        filterChain.doFilter(request,response);
    }
}
