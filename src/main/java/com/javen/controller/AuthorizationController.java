package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.javen.model.Authorization;
import com.javen.service.IAuthorizationService;
import com.javen.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 处理有关于用户权限的操作
 */
@Controller
@RequestMapping("/authorization")
public class AuthorizationController {

    private static Logger logger=Logger.getLogger(AuthorizationController.class);

    @Resource
    private IAuthorizationService authorizationService;

    /**
     * 查询权限
     * @param request
     * @param response
     */
    @RequestMapping("/get")
    public void selectAuthorization(HttpServletRequest request, HttpServletResponse response) {
        Authorization authorization = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            authorization = (Authorization) this.authorizationService.get(id);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        myUtils.printObjectMsg(request,response,authorization);
    }

    /**
     * 查询所有权限
     * @param request
     * @param response
     */
    @RequestMapping("/query")
    public void getAllAuthorizations(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Authorization> listAuthorization =  authorizationService.query();
            response.setCharacterEncoding("UTF-8");
            myUtils.printListMsg(response,listAuthorization);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 添加权限
     * @param request
     * @param response
     */
    @RequestMapping("/add")
    public void addAuthorization(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Authorization authorization = new Authorization();
            int role = Integer.parseInt(request.getParameter("role"));
            int auth = Integer.parseInt(request.getParameter("auth"));
            authorization.setRole(role);
            authorization.setAuth(auth);
            this.authorizationService.add(authorization);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 删除权限
     * @param request
     * @param response
     */
    @RequestMapping("/delete")
    public void deleteLevelById(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            logger.info("deleteLevelById! id="+id);
            this.authorizationService.delete(id);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 更新权限
     * @param request
     * @param response
     */
    @RequestMapping("/update")
    public void updateAuthorization(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Authorization authorization = new Authorization();
            int id = Integer.parseInt(request.getParameter("id"));
            logger.info("updateAuthorization! id="+id);
            int role = Integer.parseInt(request.getParameter("role"));
            int auth = Integer.parseInt(request.getParameter("auth"));
            authorization.setId(id);
            authorization.setRole(role);
            authorization.setAuth(auth);
            this.authorizationService.update(authorization);
            logger.info("管理员在时间"+new Date()+"修改权限设置："+authorization);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
        }
        myUtils.printMsg(request,response,message);
    }
}
