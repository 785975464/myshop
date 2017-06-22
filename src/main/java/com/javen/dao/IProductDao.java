package com.javen.dao;

import com.javen.model.Product;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IProductDao {
    public Product getProductById(int id);

    public List<Product> getAllProducts();

    public void addProduct(Product product);

}