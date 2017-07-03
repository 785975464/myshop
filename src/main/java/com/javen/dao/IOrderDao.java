package com.javen.dao;

import com.javen.model.Order;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IOrderDao extends IBaseDao<Order,Integer>{

    List<Order> getOrders(int uid);

    List<Order> getOrdersByStatus(int solve);

    List<Order> getOrdersByCloseStatus(boolean close, int uid);

    Order getLatestOrderByUserId(int uid);

}
