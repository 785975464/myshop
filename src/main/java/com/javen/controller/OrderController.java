package com.javen.controller;

/**
 * Created by Jay on 2017/6/21.
 */

import com.javen.model.Order;
import com.javen.model.Product;
import com.javen.model.User;
import com.javen.service.IOrderService;
import com.javen.service.IProductService;
import com.javen.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 处理有关于订单的操作
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger logger=Logger.getLogger(OrderController.class);

    @Resource
    private IOrderService orderService;
    @Resource
    private IProductService productService;
    @Autowired
    private TaskExecutor executor;

    /**
     * 查询订单
     * @param request
     * @param response
     */
    @RequestMapping("/get")
    public void getOrderById(HttpServletRequest request, HttpServletResponse response) {
        Order order = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            order = (Order) this.orderService.get(id);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        myUtils.printObjectMsg(request,response,order);
    }

    /**
     * 获取用户最新订单
     * @param request
     * @param response
     */
    @RequestMapping("/getLatest")
    public void getLatestOrder(HttpServletRequest request, HttpServletResponse response) {
        Order order = null;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            User user = myUtils.getCurrentLocalUser(request);
            order = orderService.getLatestOrderByUserId(user.getId());
            logger.info("getCurrentOrder order:" + order.getId() + " " + order.getDatetime() + " " + user.getUsername());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        String listjson = JsonUtil.objectToJson(order);
        myUtils.printObjectMsg(request, response, listjson);
    }

    /**
     * 获取所有订单
     * @param request
     * @param response
     */
    @RequestMapping("/getOrders")
    public void getOrdersByUserId(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            User user = myUtils.getCurrentLocalUser(request);
            List<Order> listOrder = this.orderService.getOrders(user.getId());
            String listjson = JsonUtil.listToJson(listOrder);
            myUtils.printListMsg(response,listOrder);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 销售商用于处理未处理的订单
     * @param request
     * @param response
     */
    @RequestMapping("/getOrders/status")
    public void getOrdersByStatus(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int solve;
            try {
                solve = Integer.parseInt(request.getParameter("solve"));
            } catch (Exception e) {
                solve = -1;
            }
            List<Order> listOrder = orderService.getOrdersByStatus(solve);
            myUtils.printListMsg(response,listOrder);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }

    /**
     * 普通用户用于处理未完成的订单
     * @param request
     * @param response
     */
    @RequestMapping("/getOrders/close")
    public void getOrdersCompleted(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String sessionid = request.getParameter("sessionid");
            HttpSession session = (HttpSession) config.sessionmap.get(sessionid);
            User user = (User) session.getAttribute("userinfo");
            if (user == null) {
                return;
            }
            boolean close;
            int uid;
            try {
                close = Boolean.parseBoolean(request.getParameter("close"));
            } catch (Exception e) {
                close = false;
            }
            uid = user.getId();
            List<Order> listOrder = orderService.getOrdersByCloseStatus(close, uid);
            myUtils.printListMsg(response,listOrder);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 查询所有订单
     * @param request
     * @param response
     */
    @RequestMapping("/query")
    public void getAllOrders(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Order> listOrder = orderService.query();
            response.setCharacterEncoding("UTF-8");
            myUtils.printListMsg(response,listOrder);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }


    /**
     * 添加订单。添加前先验证用户，用线程池+同步块完成订单插入操作
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/add")
    public void addOrder(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        User user = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            user = myUtils.getCurrentLocalUser(request);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        if (user == null) {
            myUtils.printMsg(request, response, config.NONE);
            return;
        }
        final Order order = new Order();
        int uid = user.getId();
        int pid = Integer.parseInt(request.getParameter("pid"));
        Date now = new Date();
        Timestamp datetime = Timestamp.valueOf(df.format(now));
        double total = Double.parseDouble(request.getParameter("total"));
        Product product = (Product) productService.get(pid);
        order.setUid(uid);
        order.setProduct(product);
        order.setDatetime(datetime);
        order.setTotal(total);
        order.setSolve(-1);
        order.setSolveremark("");
        order.setClose(false);
        order.setCloseremark("");
        executor.execute(new Runnable() {
            public void run() {
                synchronized (orderService) {
                    try {
                        orderService.add(order);
                        logger.info("当前订单号为：" + order.getId());
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 删除订单。暂未开放删除接口
     * @param request
     * @param response
     */
    @RequestMapping("/deleteOrderWithAdminAuth")
    public void deleteOrderById(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            logger.info("deleteOrderById! id=" + id);
            this.orderService.delete(id);
            message = config.SUCCESS;
        } catch (Exception e) {
            message = config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }

    /**
     * 仅更新订单状态
     * @param request
     * @param response
     */
    @RequestMapping("/update")
    public void updateOrder(HttpServletRequest request, HttpServletResponse response) {
        String message;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            logger.info("updateOrder! id=" + id);
            Order order = (Order) orderService.get(id);
            Integer solve = Integer.parseInt(request.getParameter("solve"));
            String solveremark = request.getParameter("solveremark");
            solveremark = java.net.URLDecoder.decode(solveremark, "UTF-8");
            boolean close = Boolean.parseBoolean(request.getParameter("close"));
            String closeremark = request.getParameter("closeremark");
            closeremark = java.net.URLDecoder.decode(closeremark, "UTF-8");
            order.setSolve(solve);
            order.setSolveremark(solveremark);
            order.setClose(close);
            order.setCloseremark(closeremark);
            this.orderService.update(order);
            message = config.SUCCESS;
        } catch (Exception e) {
            message = config.ERROR;
            logger.error(e.getMessage());
        }
        myUtils.printMsg(request,response,message);
    }
}
