package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.model.Product;
import com.javen.service.IProductService;
import com.javen.util.JsonUtil;
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
    // /user/test?id=1
    @RequestMapping("/getProductById")
    public void selectCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = this.productService.getProductById(id);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(product));
        response.getWriter().close();
    }

    @RequestMapping("/getAllProducts")
    public void getAllProducts(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> listProduct =  productService.getAllProducts();
        System.out.println("getAllProducts! listProduct.size():"+listProduct.size());

//        Map<String,Object> jsonMap = new HashMap<String, Object>();
        for (int i=0;i<listProduct.size();i++){
//            jsonMap.put(listUser.get(i).getUsername(),listUser.get(i).getPassword());
            System.out.println(listProduct.get(i).toString());
        }

        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listProduct);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listProduct.size()+",\"recordsFiltered\":"+listProduct.size()+"}";
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/addProduct")
    public void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("addProduct!");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            Product product = new Product();
            String name = request.getParameter("name");
    //        String pic = request.getParameter("pic");
            String remark = request.getParameter("remark");
            String xremark = request.getParameter("xremark");
    //        String date = request.getParameter("date");
            double price;
            try {
                price = Double.parseDouble(request.getParameter("price"));
            }catch (Exception e){
                price=0;
                e.printStackTrace();
            }
            int number;
            try {
                number = Integer.parseInt(request.getParameter("number"));
            }catch (Exception e){
                number=0;
                e.printStackTrace();
            }
            int cid;    //默认分类cid=-1
            try {
                cid = Integer.parseInt(request.getParameter("cid"));
            }catch (Exception e){
                cid=0;
                e.printStackTrace();
            }
            product.setName(name);
            product.setPrice(price);
            product.setNumber(number);
            product.setRemark(remark);
            product.setXremark(xremark);
            product.setCid(cid);
            this.productService.addProduct(product);
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
