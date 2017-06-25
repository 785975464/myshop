package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.IOrderDao;
import com.javen.model.Order;
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

    @Override
    public IBaseDao getDao(){
        return orderDao;
    }

    public List<Order> getOrders(int uid){ return orderDao.getOrders(uid);}

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
