package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.ICategoryDao;
import com.javen.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public IBaseDao getDao(){
        return categoryDao;
    }

}
