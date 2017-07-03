package com.javen.service;

import com.javen.model.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Transactional
public interface IProductService extends IBaseService{

    List<Product> queryByCategoryId(int id);

    List<Product> getProductsBySeller(int id);

    void updateProductNumber(int id);

}
