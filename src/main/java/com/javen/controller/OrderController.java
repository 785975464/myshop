package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.model.Order;
import com.javen.model.Product;
import com.javen.model.User;
import com.javen.service.IOrderService;
import com.javen.service.IProductService;
import com.javen.util.JsonUtil;
import com.javen.util.config;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


@Controller
@RequestMapping("/order")
// /user/**
public class OrderController {

    @Resource
    private IOrderService orderService;
    @Resource
    private IProductService productService;

    @RequestMapping("/get")
    public void getOrderById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        Order order = (Order) this.orderService.get(id);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(order));
        response.getWriter().close();
    }

    @RequestMapping("/getCurrent")
    public void getCurrentOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Order order=(Order)config.sessionmap.get("orderinfo");
        System.out.println("getCurrentOrder order:"+order.getId()+" "+order.getDatetime());
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(order));
        response.getWriter().close();
    }

    @RequestMapping("/getOrders")
    public void getOrdersByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        User user = (User)this.userService.get(config.userID);
        User user = (User)config.sessionmap.get("userinfo");
        if (user!=null) {
            List<Order> listOrder = this.orderService.getOrders(user.getId());
            String listjson = JsonUtil.listToJson(listOrder);
            String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listOrder.size()+",\"recordsFiltered\":"+listOrder.size()+"}";
            System.out.println("jsonstring:"+jsonstring);
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }
    }

    @RequestMapping("/getOrdersByStatus")   //销售商用于处理未处理的订单
    public void getOrdersByStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        int solve;
        try {
            solve = Integer.parseInt(request.getParameter("solve"));
        }catch (Exception e){
            solve=-1;
        }
        List<Order> listOrder = orderService.getOrdersByStatus(solve);
        String listjson = JsonUtil.listToJson(listOrder);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listOrder.size()+",\"recordsFiltered\":"+listOrder.size()+"}";
        System.out.println("jsonstring:"+jsonstring);
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/getOrders/close")        //普通用户用于处理未完成的订单
    public void getOrdersCompleted(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        boolean close;
        int uid;
        try {
            close = Boolean.parseBoolean(request.getParameter("close"));
        }catch (Exception e){
            close=false;
        }
        User user = (User) config.sessionmap.get("userinfo");
        uid = user.getId();
        List<Order> listOrder = orderService.getOrdersByCloseStatus(close,uid);
        String listjson = JsonUtil.listToJson(listOrder);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listOrder.size()+",\"recordsFiltered\":"+listOrder.size()+"}";
        System.out.println("jsonstring:"+jsonstring);
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/query")
    public void getAllOrders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Order> listOrder =  orderService.query();
        System.out.println("getAllOrders! listOrder.size():"+listOrder.size());
        for (int i=0;i<listOrder.size();i++){
            System.out.println(listOrder.get(i).toString());
        }
        response.setCharacterEncoding("UTF-8");
        String listjson = JsonUtil.listToJson(listOrder);
        String jsonstring="{\"data\":"+listjson+",\"draw\":\"1\",\"recordsTotal\":"+listOrder.size()+",\"recordsFiltered\":"+listOrder.size()+"}";
        System.out.println("jsonstring:"+jsonstring);
        PrintWriter out = response.getWriter();
        out.print(jsonstring);
        out.flush();
        out.close();
    }

    @RequestMapping("/add")
    public void addOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("addOrder!");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //添加订单前先验证用户
        String message="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (config.sessionID==null || config.sessionID.equals("")){
            message="none";
        }
        HttpSession session = (HttpSession)config.sessionmap.get(config.sessionID);
        if (session==null || session.getAttribute("username")==null || session.getAttribute("username").equals("")){
            System.out.println("session为空");
            message="none";
        }
        if (message.equals("none")){
            String jsonstring = JsonUtil.msgToJson(message);
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
            return;
        }
        System.out.println("session: id="+session.getAttribute("id")+" username="+session.getAttribute("username"));
        try {
            Order order = new Order();
            int uid = Integer.parseInt(session.getAttribute("id").toString());
            int pid = Integer.parseInt(request.getParameter("pid"));
            Long time = new Long(request.getParameter("datetime"));
            Timestamp datetime = Timestamp.valueOf(df.format(time));
//            Timestamp datetime = Timestamp.valueOf(request.getParameter("datetime"));
//            Timestamp datetime = Timestamp.valueOf(df.format(request.getParameter("datetime")));
            double total = Double.parseDouble(request.getParameter("total"));
            Product product=(Product) productService.get(pid);  //获取当前订单物品
            order.setUid(uid);
            order.setProduct(product);
            order.setDatetime(datetime);
            order.setTotal(total);
            order.setSolve(-1);    //默认为-1
            order.setSolveremark("");
            order.setClose(false);
            order.setCloseremark("");
            orderService.add(order);
            System.out.println("当前订单号为："+order.getId());      //获得当前订单号
            config.sessionmap.put("orderinfo",order);
            message="success";
        }catch (Exception e){
            message="error";
            e.printStackTrace();
        }finally {
            String jsonstring = JsonUtil.msgToJson(message);
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }
    }

    @RequestMapping("/delete")
    public void deleteOrderById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("deleteOrderById! id="+id);
            this.orderService.delete(id);
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
    public void updateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
            Order order = new Order();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("updateOrder! id="+id);
            int uid = Integer.parseInt(request.getParameter("uid"));
            int pid = Integer.parseInt(request.getParameter("pid"));
            Timestamp datetime = Timestamp.valueOf(request.getParameter("datetime"));
            double total = Double.parseDouble(request.getParameter("total"));
            Integer solve = Integer.parseInt(request.getParameter("solve"));
            String solveremark = request.getParameter("solveremark");
            boolean close = Boolean.parseBoolean(request.getParameter("close"));
            String closeremark = request.getParameter("closeremark");
            order.setId(id);
            order.setUid(uid);
            Product product = (Product) productService.get(pid);
            order.setProduct(product);
//            order.setPid(pid);
            order.setDatetime(datetime);
            order.setTotal(total);
            order.setSolve(solve);
            order.setSolveremark(solveremark);
            order.setClose(close);
            order.setCloseremark(closeremark);
            this.orderService.update(order);
            message="success";
//            config.sessionmap.put("orderinfo",order);
        }catch (Exception e){
            message="error";
            e.printStackTrace();
        }finally {
            String jsonstring = JsonUtil.msgToJson(message);
            PrintWriter out = response.getWriter();
            out.print(jsonstring);
            out.flush();
            out.close();
        }
    }
}
