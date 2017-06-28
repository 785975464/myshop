package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.model.Category;
import com.javen.service.ICategoryService;
import com.javen.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/category")
// /user/**
public class CategoryController {
//    private static Logger log=LoggerFactory.getLogger(UserController.class);

    @Resource
    private ICategoryService categoryService;
    // /user/test?id=1
    @RequestMapping("/get")
    public void selectCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = (Category) this.categoryService.get(id);
//        Category category = this.categoryService.getCategoryById(id);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(category));
        response.getWriter().close();
    }

    @RequestMapping("/query")
    public void getAllCategorys(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("getAllCategorys!");
        List<Category> listCategory =  categoryService.query();
        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listCategory);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listCategory.size()+",\"recordsFiltered\":"+listCategory.size()+"}";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/add")
    public void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("addCategory!");
        String message=null;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Category category = new Category();
            String type = request.getParameter("type");
            category.setType(type);
            this.categoryService.add(category);
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
    public void deleteCategoryById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("deleteCategoryById! id="+id);
            this.categoryService.delete(id);
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
    public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message=null;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Category category = new Category();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("updateCategory! id="+id);
            String type = request.getParameter("type");
            category.setId(id);
            category.setType(type);
            this.categoryService.update(category);
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


//
//    @RequestMapping("/getAllCategorys")
//    public void getAllProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        List<Category> listCategory =  categoryService.getAllCategorys();
//        System.out.println("getAllProducts! listProduct.size():"+listCategory.size());
//        for (int i=0;i<listCategory.size();i++){
//            System.out.println(listCategory.get(i).toString());
//        }
//        response.setCharacterEncoding("UTF-8");
//        String listjson = JsonUtil.listToJson(listCategory);
//        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listCategory.size()+",\"recordsFiltered\":"+listCategory.size()+"}";
//        PrintWriter out = response.getWriter();
//        out.print(jsonstring);
//        out.flush();
//        out.close();
//    }
//
//    @RequestMapping("/addCategory")
//    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        System.out.println("addCategory!");
//        String message=null;
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            Category category = new Category();
//            String type = request.getParameter("type");
//            category.setType(type);
//            this.categoryService.addCategory(category);
//            message="success";
//        }catch (Exception e){
//            message="error";
//        }finally {
//            String jsonstring = JsonUtil.msgToJson(message);
//            PrintWriter out = response.getWriter();
//            out.print(jsonstring);
//            out.flush();
//            out.close();
//        }
//    }
//
//    @RequestMapping("/deleteCategoryById")
//    public void deleteProductById(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        String message=null;
//        try {
//            int id = Integer.parseInt(request.getParameter("id"));
//            System.out.println("deleteCategoryById! id="+id);
//            this.categoryService.deleteCategoryById(id);
//            message="success";
//        }catch (Exception e){
//            message="error";
//        }finally {
//            String jsonstring = JsonUtil.msgToJson(message);
//            PrintWriter out = response.getWriter();
//            out.print(jsonstring);
//            out.flush();
//            out.close();
//        }
//    }
//
//    @RequestMapping("/updateCategory")
//    public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String message=null;
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        try {
//            Category category = new Category();
//            int id = Integer.parseInt(request.getParameter("id"));
//            System.out.println("updateCategory! id="+id);
//            String type = request.getParameter("type");
//            category.setId(id);
//            category.setType(type);
//            this.categoryService.updateCategory(category);
//            message="success";
//        }catch (Exception e){
//            message="error";
//        }finally {
//            String jsonstring = JsonUtil.msgToJson(message);
//            PrintWriter out = response.getWriter();
//            out.print(jsonstring);
//            out.flush();
//            out.close();
//        }
//    }
}
