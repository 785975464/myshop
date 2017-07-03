package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.IProductDao;
import com.javen.model.Product;
import com.javen.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Override
    public IBaseDao getDao(){
        return productDao;
    }

    public List<Product> queryByCategoryId(int id){ return productDao.queryByCategoryId(id);}

    public List<Product> getProductsBySeller(int id){ return productDao.getProductsBySeller(id);}

    public void updateProductNumber(int id){ productDao.updateProductNumber(id);}

}
