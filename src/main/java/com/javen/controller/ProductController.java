package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.javen.model.Category;
import com.javen.model.Product;
import com.javen.model.User;
import com.javen.service.ICategoryService;
import com.javen.service.IProductService;
import com.javen.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 处理有关于商品的操作
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private static Logger logger=Logger.getLogger(ProductController.class);
    @Resource
    private IProductService productService;
    @Resource
    private ICategoryService categoryService;

    /**
     * 根据商品ID返回商品
     * @param request
     * @param response
     */
    @RequestMapping("/get")
    public void selectCategory(HttpServletRequest request, HttpServletResponse response) {
        Product product = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            product = (Product) this.productService.get(id);
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        myUtils.printQueryMsg(response,product);
    }

    /**
     * 查询所有商品
     * @param request
     * @param response
     */
    @RequestMapping("/query")
    public void getAllProducts(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Product> listProduct =  productService.query();
            response.setCharacterEncoding("UTF-8");
            myUtils.printListMsg(response,listProduct);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 根据销售商ID查询商品
     * @param request
     * @param response
     */
    @RequestMapping("/query/seller")
    public void getProductsBySeller(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = myUtils.getUserByCookie(request);
            logger.info("userinfo:"+user);
            List<Product> listProduct =  productService.getProductsBySeller(user.getId());
            response.setCharacterEncoding("UTF-8");
            myUtils.printListMsg(response,listProduct);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 通过类别查询商品
     * @param request
     * @param response
     */
    @RequestMapping("/query/categoryid")
    public void getProductsByCategoryId(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            List<Product> listProduct =  productService.queryByCategoryId(id);
            response.setCharacterEncoding("UTF-8");
            myUtils.printListMsg(response,listProduct);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 添加商品
     * @param request
     * @param response
     */
    @RequestMapping("/add")
    public void addProduct(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            User user = myUtils.getCurrentLocalUser(request);
            Product product = new Product();
            String name = request.getParameter("name");
            name = java.net.URLDecoder.decode(name, "UTF-8");
            String remark = request.getParameter("remark");
            remark = java.net.URLDecoder.decode(remark, "UTF-8");
            String xremark = request.getParameter("xremark");
            xremark = java.net.URLDecoder.decode(xremark, "UTF-8");
            double price;
            try {
                price = Double.parseDouble(request.getParameter("price"));
            }catch (Exception e){
                price=0;
            }
            int number;
            try {
                number = Integer.parseInt(request.getParameter("number"));
            }catch (Exception e){
                number=0;
            }
            int cid;
            try {
                cid = Integer.parseInt(request.getParameter("cid"));
            }catch (Exception e){
                cid=0;
            }
            int sid = user.getId();
            product.setName(name);
            product.setPrice(price);
            product.setNumber(number);
            product.setPicture("/myshop/pimages/a.jpg");
            product.setRemark(remark);
            product.setXremark(xremark);
            Category category=(Category) categoryService.get(cid);
            product.setCategory(category);
            product.setSid(sid);
            product.setIsdeleted(false);
            this.productService.add(product);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 删除商品
     * @param request
     * @param response
     */
    @RequestMapping("/delete")
    public void deleteProductById(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            logger.info("deleteProductById! id="+id);
            this.productService.delete(id);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 更新商品
     * @param request
     * @param response
     */
    @RequestMapping("/update")
    public void updateProduct(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Product product = new Product();
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            name = java.net.URLDecoder.decode(name, "UTF-8");
            double price = Double.parseDouble(request.getParameter("price"));
            int number = Integer.parseInt(request.getParameter("number"));
            String remark = request.getParameter("remark");
            remark = java.net.URLDecoder.decode(remark, "UTF-8");
            String xremark = request.getParameter("xremark");
            xremark = java.net.URLDecoder.decode(xremark, "UTF-8");
            int cid = Integer.parseInt(request.getParameter("cid"));
            Category category=(Category) categoryService.get(cid);
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setNumber(number);
            product.setRemark(remark);
            product.setXremark(xremark);
            product.setCategory(category);
            this.productService.update(product);
            logger.info("销售商在时间"+new Date()+"修改商品设置："+product);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 更新商品数量-1
     * @param request
     * @param response
     */
    @RequestMapping("/update/number")
    public void updateNumber(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            Product product = new Product();
            int id = Integer.parseInt(request.getParameter("id"));
            logger.info("updateProductNumber! id="+id);
            int number = Integer.parseInt(request.getParameter("number"));
            product.setNumber(number);
            this.productService.updateProductNumber(id);
            message=config.SUCCESS;
        }catch (Exception e){
            message=config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }
}
