package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.IOrderDao;
import com.javen.dao.IProductDao;
import com.javen.model.Order;
import com.javen.model.Product;
import com.javen.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private IProductDao productDao;

    public IBaseDao getDao(){
        return orderDao;
    }

    public List<Order> getOrders(int uid){ return orderDao.getOrders(uid);}

    public List<Order> getOrdersByStatus(int solve){ return orderDao.getOrdersByStatus(solve);}

    public List<Order> getOrdersByCloseStatus(boolean close, int uid){ return orderDao.getOrdersByCloseStatus(close,uid);}

    public Order getLatestOrderByUserId(int uid){ return orderDao.getLatestOrderByUserId(uid);}

    public void add(Order order) {
        Product product = order.getProduct();
        System.out.println("当前库存："+product.getNumber());
        if (product.getNumber()<1){
            order.setClose(true);
            order.setCloseremark("库存不足，订单已取消！");
        }
        else {
            productDao.updateProductNumber(product.getId());
            System.out.println("成功更新库存！");
        }
        System.out.println("order:"+order);
        orderDao.add(order);
    }

    //    @Resource
//    private IOrderDao orderDao;
//
//    public Order getOrderById(int id) {
//        return this.orderDao.getOrderById(id);
//    }
//
//    public List<Order> getAllOrders() {
//        // TODO Auto-generated method stub
//        List<Order> getAllOrders = orderDao.getAllOrders();
//        return getAllOrders;
//    }
//
//    public void addOrder(Order order){
//        orderDao.addOrder(order);
//    }
//
//    public void deleteOrderById(int id){ orderDao.deleteOrderById(id);}
//
//    public void updateOrder(Order order){ orderDao.updateOrder(order); }

}
