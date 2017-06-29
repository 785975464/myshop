package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.model.Level;
import com.javen.model.User;
import com.javen.service.ILevelService;
import com.javen.service.IUserService;
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
@RequestMapping("/level")
// /user/**
public class LevelController {
//    private static Logger log=LoggerFactory.getLogger(UserController.class);

    @Resource
    private ILevelService levelService;
    @Resource
    private IUserService userService;

    @RequestMapping("/get")
    public void selectCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        Level level = (Level) this.levelService.get(id);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(level));
        response.getWriter().close();
    }

    @RequestMapping("/query")
    public void getAllLevels(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Level> listLevel =  levelService.query();
//        System.out.println("getAllLevels! listLevel.size():"+listLevel.size());
//        for (int i=0;i<listLevel.size();i++){
//            System.out.println(listLevel.get(i).toString());
//        }
        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listLevel);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listLevel.size()+",\"recordsFiltered\":"+listLevel.size()+"}";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/add")
    public void addLevel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("addLevel!");
        String message=null;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Level level = new Level();
            int l = Integer.parseInt(request.getParameter("level"));
            double discount = Double.parseDouble(request.getParameter("discount"));
            int credit = Integer.parseInt(request.getParameter("credit"));
            level.setLevel(l);
            level.setDiscount(discount);
            level.setCredit(credit);
            this.levelService.add(level);
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
            this.levelService.delete(id);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/update")
    public void updateLevel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message=null;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Level level = new Level();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("updateLevel! id="+id);
            int l = Integer.parseInt(request.getParameter("level"));
            double discount = Double.parseDouble(request.getParameter("discount"));
            int credit = Integer.parseInt(request.getParameter("credit"));
            level.setId(id);
            level.setLevel(l);
            level.setDiscount(discount);
            level.setCredit(credit);
            this.levelService.update(level);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/getDiscount")
    public void getDiscountByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

//        Cookie[] cookies = request.getCookies();    //从用户的cookie中取出用户信息
//        System.out.println("getCurrentUser! cookies:"+cookies);
//        User user=null;
//        if (cookies!=null && cookies.length>0) {
//            for (Cookie c : cookies) {
//                System.out.println(c.getName() + "--->" + c.getValue());
//                if (c.getName().equals("sessionid")) {
//                    String sessionid = c.getValue();
//                    HttpSession session = (HttpSession) config.sessionmap.get(sessionid);
//                    if (session == null) {
//                        System.out.println("session为空！");
//                    } else {
//                        System.out.println("session不为空！");
//                        int id = (Integer) session.getAttribute("id");
//                        user = (User) userService.get(id);
//                    }
//                    break;
//                }
//            }
//        }
        User user=myUtils.getCurrentLocalUser(request);
        if (user!=null) {       //获取当前用户的折扣
            Level level = this.levelService.getDiscount(user.getLevel());
            myUtils.printMsg(request,response,String.valueOf(level.getDiscount()));
//            ObjectMapper mapper = new ObjectMapper();
//            response.getWriter().write(mapper.writeValueAsString(level));
//            response.getWriter().close();
        }
        else{
            myUtils.printMsg(request,response,"error");
//            ObjectMapper mapper = new ObjectMapper();
//            response.getWriter().write(mapper.writeValueAsString("error"));
//            response.getWriter().close();
        }
    }
}
