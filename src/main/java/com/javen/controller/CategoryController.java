package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.javen.model.Category;
import com.javen.service.ICategoryService;
import com.javen.util.config;
import com.javen.util.myUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 处理有关于商品类别的操作
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    private static Logger logger= LoggerFactory.getLogger(CategoryController.class);

    @Resource
    private ICategoryService categoryService;

    /**
     * 根据类别ID返回商品类别
     * @param request
     * @param response
     */
    @RequestMapping("/get")
    public void selectCategory(HttpServletRequest request, HttpServletResponse response) {
        Category category = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            category = (Category) this.categoryService.get(id);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        myUtils.printQueryMsg(response,category);
    }

    /**
     * 查询所有类别
     * @param request
     * @param response
     */
    @RequestMapping("/query")
    public void getAllCategorys(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Category> listCategory =  categoryService.query();
            response.setCharacterEncoding("UTF-8");
            myUtils.printListMsg(response,listCategory);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 添加类别
     * @param request
     * @param response
     */
    @RequestMapping("/add")
    public void addCategory(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Category category = new Category();
            String type = request.getParameter("type");
            type = java.net.URLDecoder.decode(type, "UTF-8");
            category.setType(type);
            category.setIsdeleted(false);
            this.categoryService.add(category);
            message= config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 删除类别
     * @param request
     * @param response
     */
    @RequestMapping("/delete")
    public void deleteCategoryById(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");int id = Integer.parseInt(request.getParameter("id"));
            logger.info("deleteCategoryById! id="+id);
            this.categoryService.delete(id);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 更新类别
     * @param request
     * @param response
     */
    @RequestMapping("/update")
    public void updateCategory(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");Category category = new Category();
            int id = Integer.parseInt(request.getParameter("id"));
            logger.info("updateCategory! id="+id);
            String type = request.getParameter("type");
            type = java.net.URLDecoder.decode(type, "UTF-8");
            category.setId(id);
            category.setType(type);
            this.categoryService.update(category);
            logger.info("销售商在时间"+new Date()+"修改类别设置："+category);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
        }
        myUtils.printMsg(request,response,message);
    }
}
