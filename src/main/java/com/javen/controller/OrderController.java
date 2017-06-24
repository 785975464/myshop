package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javen.model.Order;
import com.javen.service.IOrderService;
import com.javen.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;


@Controller
@RequestMapping("/order")
// /user/**
public class OrderController {

    @Resource
    private IOrderService orderService;

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
        String message=null;
        try {
            Order order = new Order();
            int uid = Integer.parseInt(request.getParameter("uid"));
            int pid = Integer.parseInt(request.getParameter("pid"));
            Timestamp datetime = Timestamp.valueOf(request.getParameter("datetime"));
            double total = Double.parseDouble(request.getParameter("total"));
//            boolean solve = Boolean.parseBoolean(request.getParameter("solve"));
//            boolean solve;
            String solveremark = request.getParameter("solveremark");
//            boolean close = Boolean.parseBoolean(request.getParameter("close"));
            boolean close = false;
            String closeremark = request.getParameter("closeremark");
//            System.out.println("datetime:"+datetime+" solve:"+solve+" close:"+close);
            order.setUid(uid);
            order.setPid(pid);
            order.setDatetime(datetime);
            order.setTotal(total);
            order.setSolve(-1);    //默认为-1
            order.setSolveremark(solveremark);
            order.setClose(close);
            order.setCloseremark(closeremark);
            orderService.add(order);
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
            order.setPid(pid);
            order.setDatetime(datetime);
            order.setTotal(total);
            order.setSolve(solve);
            order.setSolveremark(solveremark);
            order.setClose(close);
            order.setCloseremark(closeremark);
            this.orderService.update(order);
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
