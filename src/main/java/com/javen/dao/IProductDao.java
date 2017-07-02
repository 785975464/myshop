package com.javen.dao;

import com.javen.model.Product;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IProductDao extends IBaseDao<Product,Integer>{

    List<Product> queryByCategoryId(int id);

    List<Product> getProductsBySeller(int id);

    void updateProductNumber(int id);
//    List<Product> getProducts();

//    public Product getProductById(int id);
//
//    public List<Product> getAllProducts();
//
//    public void addProduct(Product product);
//
//    public void deleteProductById(int id);
//
//    public void updateProduct(Product product);

}
