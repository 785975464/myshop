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
import com.javen.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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

//    @RequestMapping("/getCurrent")
//    public void getCurrentOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        Order order=(Order)config.sessionmap.get("orderinfo");
//        System.out.println("getCurrentOrder order:"+order.getId()+" "+order.getDatetime());
//        ObjectMapper mapper = new ObjectMapper();
//        response.getWriter().write(mapper.writeValueAsString(order));
//        response.getWriter().close();
//    }

    @RequestMapping("/getLatest")
    public void getLatestOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        User user = myUtils.getCurrentLocalUser(request);
        Order order = orderService.getLatestOrderByUserId(user.getId());
//        Order order=(Order)config.sessionmap.get("orderinfo");
        System.out.println("getCurrentOrder order:"+order.getId()+" "+order.getDatetime()+" "+user.getUsername());
//        ObjectMapper mapper = new ObjectMapper();
//        response.getWriter().write(mapper.writeValueAsString(order));
//        response.getWriter().close();
        String listjson = JsonUtil.objectToJson(order);
        myUtils.printObjectMsg(request,response,listjson);
    }

    @RequestMapping("/getOrders")
    public void getOrdersByUserId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        User user = (User)this.userService.get(config.userID);
//        User user = (User)config.sessionmap.get("userinfo");
        User user = myUtils.getCurrentLocalUser(request);
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

    @RequestMapping("/getOrders/status")   //销售商用于处理未处理的订单
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
//        Cookie[] cookies = request.getCookies();    //从用户的cookie中取出用户信息
//        System.out.println("getOrdersCompleted! cookies:"+cookies);       //此处发现cookie为空，为什么？？
        String sessionid = request.getParameter("sessionid");
        HttpSession session = (HttpSession)config.sessionmap.get(sessionid);
        User user = (User)session.getAttribute("userinfo");
        if (user==null){
            return;
        }
        boolean close;
        int uid;
        try {
            close = Boolean.parseBoolean(request.getParameter("close"));
        }catch (Exception e){
            close=false;
        }
//        User user = (User) config.sessionmap.get("userinfo");
//        User user = myUtils.getCurrentLocalUser(request);
//        if (user==null){
//            return;
//        }
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

    @Autowired
    private TaskExecutor executor;

    @RequestMapping("/add")
    public void addOrder(final HttpServletRequest request,final HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //添加订单前先验证用户
        String message="";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user=myUtils.getCurrentLocalUser(request);
        if (user==null){
            message="none";
            myUtils.printMsg(request,response,message);
            return;
        }
//            System.out.println("session: id="+session.getAttribute("id")+" username="+session.getAttribute("username"));
        final Order order = new Order();
//            int uid = Integer.parseInt(session.getAttribute("id").toString());
        int uid = user.getId();
        int pid = Integer.parseInt(request.getParameter("pid"));
//            Long time = new Long(request.getParameter("datetime"));
        Date now = new Date();
        Timestamp datetime = Timestamp.valueOf(df.format(now));
        double total = Double.parseDouble(request.getParameter("total"));
        Product product=(Product) productService.get(pid);  //获取当前订单物品
        order.setUid(uid);
        order.setProduct(product);
        order.setDatetime(datetime);
        order.setTotal(total);
        order.setSolve(-1);    //默认为-1，待处理
        order.setSolveremark("");
        order.setClose(false);
        order.setCloseremark("");
        //线程池+同步块
        executor.execute(new Runnable() {
            public void run() {
                synchronized (orderService) {
                    System.out.println("addOrder!");
                    try {
                        //将订单添加到队列中
            //            config.queue.offer(order)
                        orderService.add(order);
                        System.out.println("当前订单号为："+order.getId());      //获得当前订单号
//                        myUtils.printMsg(request,response,"success");

                    //            config.sessionmap.put("orderinfo",order);
//                        message="success";
                    }catch (Exception e){
//                        message="error";
                        e.printStackTrace();
                    }//finally {
//                        myUtils.printMsg(request,response,"success");
//                    }
                }
            }
        });
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

    @RequestMapping("/update")      //更新订单状态
    public void updateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String message=null;
        try {
//            Order order = new Order();
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("updateOrder! id="+id);
            Order order = (Order) orderService.get(id);     //部分数据改为由后端提供，仅修改订单状态
//            int uid = Integer.parseInt(request.getParameter("uid"));
//            int pid = Integer.parseInt(request.getParameter("pid"));
//            Timestamp datetime = Timestamp.valueOf(request.getParameter("datetime"));
//            double total = Double.parseDouble(request.getParameter("total"));
            Integer solve = Integer.parseInt(request.getParameter("solve"));
            String solveremark = request.getParameter("solveremark");
            solveremark = java.net.URLDecoder.decode(solveremark, "UTF-8");  //前台编码
            boolean close = Boolean.parseBoolean(request.getParameter("close"));
            String closeremark = request.getParameter("closeremark");
            closeremark = java.net.URLDecoder.decode(closeremark, "UTF-8");  //前台编码

//            System.out.println(order.getProduct().getId());
//            Order order = new Order();      //要存储的order
//            order.setId(id);
//            order.setUid(uid);
//            Product product = (Product) productService.get(pid);
//            order.setProduct(product);
//            order.setDatetime(datetime);
//            order.setTotal(total);
            order.setSolve(solve);
            order.setSolveremark(solveremark);
            order.setClose(close);
            order.setCloseremark(closeremark);
            this.orderService.update(order);
            message="success";
        }catch (Exception e){
            message="error";
            e.printStackTrace();
        }finally {
            myUtils.printMsg(request,response,message);
        }
    }
}
