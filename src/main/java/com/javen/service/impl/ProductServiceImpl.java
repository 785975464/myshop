package com.javen.service.impl;

import com.javen.dao.IProductDao;
import com.javen.model.Product;
import com.javen.service.IProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("productService")
public class ProductServiceImpl implements IProductService {

    @Resource
    private IProductDao productDao;

    public Product getProductById(int id) {
        return this.productDao.getProductById(id);
    }

    public List<Product> getAllProducts() {
        // TODO Auto-generated method stub
        List<Product> getAllProducts = productDao.getAllProducts();
        return getAllProducts;
    }

    public void addProduct(Product product){
        productDao.addProduct(product);
    }

    public void deleteProductById(int id){ productDao.deleteProductById(id); }

    public void updateProduct(Product product){
        productDao.updateProduct(product);
    }


}
