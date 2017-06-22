package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.service.IUserService;
import com.javen.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.javen.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/user")
// /user/**
public class UserController {
//    private static Logger log=LoggerFactory.getLogger(UserController.class);

    @Resource
    private IUserService userService;
    // /user/test?id=1
    @RequestMapping("/getUserById")
    public void getUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(userId);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(user));
        response.getWriter().close();
    }

    @RequestMapping("/getAllUsers")
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<User> listUser =  userService.getAllUsers();
        System.out.println("getAllUsers! listUser.size():"+listUser.size());

//        Map<String,Object> jsonMap = new HashMap<String, Object>();
        for (int i=0;i<listUser.size();i++){
//            jsonMap.put(listUser.get(i).getUsername(),listUser.get(i).getPassword());
            System.out.println(listUser.get(i).toString());
        }
//        response.setContentType("application/json");
//        response.setHeader("Pragma","No-cache");
//        response.setCharacterEncoding("UTF-8");
//        PrintWriter out = null;
//        out = response.getWriter();
//        out.print(JsonUtil.toJSONString(jsonMap));
//        out.flush();
//        out.close();

//        request.setAttribute("listUser", listUser);
//        return "/allUser";

        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listUser);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listUser.size()+",\"recordsFiltered\":"+listUser.size()+"}";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
//        response.getWriter().write(jsonstring);  //以这种方式传值json
    }

    @RequestMapping("/addUser")
    public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("addUser!");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            User user = new User();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String birthday = request.getParameter("birthday");
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            int role;     //默认角色role=1，普通用户
            try {
                role = Integer.parseInt(request.getParameter("role"));
            }catch (Exception e){
                role=1;
                e.printStackTrace();
            }
            int level;    //默认等级level=0，最低等级
            try {
                level = Integer.parseInt(request.getParameter("level"));
            }catch (Exception e){
                level=0;
                e.printStackTrace();
            }
            user.setUsername(username);
            user.setPassword(password);
            user.setBirthday(birthday);
            user.setGender(gender);
            user.setPhone(phone);
            user.setEmail(email);
            user.setRole(role);
            user.settLevel(level);
            user.setLogin(false);
            this.userService.addUser(user);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            String jsonstring = JsonUtil.msgToJson(message);
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }
    }
}
