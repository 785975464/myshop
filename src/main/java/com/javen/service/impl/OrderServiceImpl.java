package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.IOrderDao;
import com.javen.dao.IProductDao;
import com.javen.model.Order;
import com.javen.model.Product;
import com.javen.service.IOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl implements IOrderService {

    private static Logger logger=Logger.getLogger(OrderServiceImpl.class);

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

    /**
     * 取出订单中的商品ID，查询现存数量，并添加订单
     * @param order
     */
    public void add(Order order) {
        Product p = order.getProduct();
        Product product=productDao.get(p.getId());
        order.setProduct(product);
        logger.info("当前库存："+product.getNumber());
        if (product.getNumber()<1){
            order.setClose(true);
            order.setCloseremark("库存不足，订单已取消！");
            logger.info("正在操作用户"+order.getUid()+"的订单，库存不足，订单已取消！");
        }
        else {
            productDao.updateProductNumber(product.getId());
            logger.info("正在操作用户"+order.getUid()+"的订单，成功更新库存！");
        }
        orderDao.add(order);
        logger.info("订单"+order.getId()+"在时间"+new Date()+"处理完毕:"+order);
    }
}
