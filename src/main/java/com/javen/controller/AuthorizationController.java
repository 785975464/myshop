package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.model.Authorization;
import com.javen.model.User;
import com.javen.service.IAuthorizationService;
import com.javen.util.JsonUtil;
import com.javen.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Controller
@RequestMapping("/authorization")

public class AuthorizationController {

    @Resource
    private IAuthorizationService authorizationService;

    @RequestMapping("/get")
    public void selectAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        Authorization authorization = (Authorization) this.authorizationService.get(id);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(authorization));
        response.getWriter().close();
    }

    @RequestMapping("/query")
    public void getAllAuthorizations(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Authorization> listAuthorization =  authorizationService.query();
        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listAuthorization);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listAuthorization.size()+",\"recordsFiltered\":"+listAuthorization.size()+"}";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/add")
    public void addAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("addAuthorization!");
        String message=null;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Authorization authorization = new Authorization();
            int role = Integer.parseInt(request.getParameter("role"));
            int auth = Integer.parseInt(request.getParameter("auth"));
            authorization.setRole(role);
            authorization.setAuth(auth);
            this.authorizationService.add(authorization);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/delete")
    public void deleteLevelById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("deleteLevelById! id="+id);
            this.authorizationService.delete(id);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/update")
    public void updateAuthorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message=null;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Authorization authorization = new Authorization();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("updateAuthorization! id="+id);
            int role = Integer.parseInt(request.getParameter("role"));
            int auth = Integer.parseInt(request.getParameter("auth"));
            authorization.setId(id);
            authorization.setRole(role);
            authorization.setAuth(auth);
            this.authorizationService.update(authorization);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

//    @RequestMapping("/checkPermission")
//    public void checkUserPermission(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        System.out.println("in checkUserPermission()!");
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        String message=null;
//        try {
//            User user = myUtils.getCurrentLocalUser(request);
//            Authorization authorization = authorizationService.getByRole(user.getRole());
//            if (authorization.getAuth()<10){
//                message="no";
//            }
//            else {
//                message="ok";
//            }
//        }catch (Exception e){
//            message="error";
//            e.printStackTrace();
//        }finally {
//            myUtils.printMsg(request,response,message);
//        }
//    }
}
