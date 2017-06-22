package com.javen.service;

import com.javen.model.Product;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IProductService {
    public Product getProductById(int id);

    public List<Product> getAllProducts();

    public void addProduct(Product product);

    public void deleteProductById(int id);

    public void updateProduct(Product product);

}
