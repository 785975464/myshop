package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.model.Category;
import com.javen.model.Product;
import com.javen.service.ICategoryService;
import com.javen.service.IProductService;
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
@RequestMapping("/product")
// /user/**
public class ProductController {

    @Resource
    private IProductService productService;
    @Resource
    private ICategoryService categoryService;

    @RequestMapping("/get")
    public void selectCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
//        Product product = this.productService.getProductById(id);
        Product product = (Product) this.productService.get(id);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(product));
        response.getWriter().close();
    }

//    @RequestMapping("/getProducts")
//    public void getProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//
//        List<Product> listProduct = productService.getProducts();
//        String listjson = JsonUtil.listToJson(listProduct);
//        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listProduct.size()+",\"recordsFiltered\":"+listProduct.size()+"}";
//        System.out.println("jsonstring:"+jsonstring);
//        PrintWriter out = response.getWriter();
//        out.print(jsonstring);
//        out.flush();
//        out.close();
//    }

    @RequestMapping("/query")
    public void getAllProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> listProduct =  productService.query();
        System.out.println("getAllProducts! listProduct.size():"+listProduct.size());
        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listProduct);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listProduct.size()+",\"recordsFiltered\":"+listProduct.size()+"}";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/query/categoryid")
    public void getProductsByCategoryId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        List<Product> listProduct =  productService.queryByCategoryId(id);
        System.out.println("getProductsByCategoryId! listProduct.size():"+listProduct.size());

        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listProduct);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listProduct.size()+",\"recordsFiltered\":"+listProduct.size()+"}";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/add")
    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("addProduct!");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            Product product = new Product();
            String name = request.getParameter("name");
            name = java.net.URLDecoder.decode(name, "UTF-8");  //前台编码
            String remark = request.getParameter("remark");
            remark = java.net.URLDecoder.decode(remark, "UTF-8");  //前台编码
            String xremark = request.getParameter("xremark");
            xremark = java.net.URLDecoder.decode(xremark, "UTF-8");  //前台编码
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
            int cid;    //默认分类cid=0
            try {
                cid = Integer.parseInt(request.getParameter("cid"));
            }catch (Exception e){
                cid=0;
                e.printStackTrace();
            }
            int sid;    //默认销售商sid=2
            try {
                sid = Integer.parseInt(request.getParameter("sid"));
            }catch (Exception e){
                sid=2;      //默认销售商为2
            }
            product.setName(name);
            product.setPrice(price);
            product.setNumber(number);
            product.setPicture("/myshop/pimages/a.jpg");
            product.setRemark(remark);
            product.setXremark(xremark);
            Category category=(Category) categoryService.get(cid);
            product.setCategory(category);
            product.setSid(2);
            product.setIsdeleted(false);
            this.productService.add(product);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/delete")
    public void deleteProductById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("deleteProductById! id="+id);
            this.productService.delete(id);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/update")
    public void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message=null;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Product product = new Product();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("updateProduct! id="+id);
            String name = request.getParameter("name");
            name = java.net.URLDecoder.decode(name, "UTF-8");  //前台编码
            double price = Double.parseDouble(request.getParameter("price"));
            int number = Integer.parseInt(request.getParameter("number"));
//            String picture = request.getParameter("picture");
            String remark = request.getParameter("remark");
            remark = java.net.URLDecoder.decode(remark, "UTF-8");  //前台编码
            String xremark = request.getParameter("xremark");
            xremark = java.net.URLDecoder.decode(xremark, "UTF-8");  //前台编码
            int cid = Integer.parseInt(request.getParameter("cid"));
//            int sid = Integer.parseInt(request.getParameter("sid"));
//            int isdeleted = Integer.parseInt(request.getParameter("isdeleted"));
            Category category=(Category) categoryService.get(cid);
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setNumber(number);
            product.setRemark(remark);
            product.setXremark(xremark);
            product.setCategory(category);
            product.setSid(2);
            this.productService.update(product);
            message="success";
        }catch (Exception e){
            message="error";
            e.printStackTrace();
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }

    @RequestMapping("/update/number")
    public void updateNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String message=null;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            Product product = new Product();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("updateProductNumber! id="+id);
            int number = Integer.parseInt(request.getParameter("number"));
            product.setNumber(number);
            this.productService.updateProductNumber(1);
            message="success";
        }catch (Exception e){
            message="error";
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }
}
