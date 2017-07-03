package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.javen.model.Level;
import com.javen.model.User;
import com.javen.service.ILevelService;
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
 * 处理有关于用户等级的操作
 */
@Controller
@RequestMapping("/level")
public class LevelController {

    private static Logger logger=Logger.getLogger(LevelController.class);

    @Resource
    private ILevelService levelService;

    /**
     * 查询等级
     * @param request
     * @param response
     */
    @RequestMapping("/get")
    public void selectLevel(HttpServletRequest request, HttpServletResponse response) {
        Level level=null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            level = (Level) this.levelService.get(id);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        myUtils.printObjectMsg(request,response,level);
    }

    /**
     * 查询所有等级
     * @param request
     * @param response
     */
    @RequestMapping("/query")
    public void getAllLevels(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Level> listLevel =  levelService.query();
            response.setCharacterEncoding("UTF-8");
            myUtils.printListMsg(response,listLevel);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 添加等级
     * @param request
     * @param response
     */
    @RequestMapping("/add")
    public void addLevel(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");Level level = new Level();
            int l = Integer.parseInt(request.getParameter("level"));
            double discount = Double.parseDouble(request.getParameter("discount"));
            int credit = Integer.parseInt(request.getParameter("credit"));
            level.setLevel(l);
            level.setDiscount(discount);
            level.setCredit(credit);
            this.levelService.add(level);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 删除等级
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
            this.levelService.delete(id);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 更新等级
     * @param request
     * @param response
     */
    @RequestMapping("/update")
    public void updateLevel(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Level level = new Level();
            int id = Integer.parseInt(request.getParameter("id"));
            int l = Integer.parseInt(request.getParameter("level"));
            double discount = Double.parseDouble(request.getParameter("discount"));
            int credit = Integer.parseInt(request.getParameter("credit"));
            level.setId(id);
            level.setLevel(l);
            level.setDiscount(discount);
            level.setCredit(credit);
            this.levelService.update(level);
            logger.info("管理员在时间"+new Date()+"修改等级设置："+level);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 根据当前用户的等级获取折扣信息
     * @param request
     * @param response
     */
    @RequestMapping("/getDiscount")
    public void getDiscountByUserId(HttpServletRequest request, HttpServletResponse response) {
        User user = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            user=myUtils.getCurrentLocalUser(request);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        if (user!=null) {
            Level level = this.levelService.getDiscount(user.getLevel());
            myUtils.printMsg(request,response,String.valueOf(level.getDiscount()));
        }
        else{
            myUtils.printMsg(request,response,config.ERROR);
        }
    }
}
