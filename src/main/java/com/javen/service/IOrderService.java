package com.javen.service;

import com.javen.model.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Transactional
public interface IOrderService extends IBaseService{

    List<Order> getOrders(int uid);

    List<Order> getOrdersByStatus(int solve);

    List<Order> getOrdersByCloseStatus(boolean close, int uid);

    Order getLatestOrderByUserId(int uid);

    void add(Order o);

    //    public Order getOrderById(int id);
//
//    public List<Order> getAllOrders();
//
//    public void addOrder(Order order);
//
//    public void deleteOrderById(int id);
//
//    public void updateOrder(Order order);

}
