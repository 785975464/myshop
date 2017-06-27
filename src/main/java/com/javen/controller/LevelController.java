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
import com.javen.util.config;
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
            String jsonstring = JsonUtil.msgToJson(message);
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
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
            String jsonstring = JsonUtil.msgToJson(message);
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
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
            String jsonstring = JsonUtil.msgToJson(message);
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }
    }

    @RequestMapping("/getDiscount")
    public void getDiscountByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        User user = (User)this.userService.get(config.userID);
        User user = (User)config.sessionmap.get("userinfo");
//        int l = Integer.parseInt(request.getParameter("level"));
        if (user!=null) {       //获取当前用户的折扣
            Level level = this.levelService.getDiscount(user.getLevel());
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(level));
            response.getWriter().close();
        }
        else{
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString("error"));
            response.getWriter().close();
        }
    }
}
