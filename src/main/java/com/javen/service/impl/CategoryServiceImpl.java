package com.javen.service.impl;

import com.javen.dao.ICategoryDao;
import com.javen.dao.IUserDao;
import com.javen.model.Category;
import com.javen.model.User;
import com.javen.service.ICategoryService;
import com.javen.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private ICategoryDao categoryDao;

    public Category getCategoryById(int id) {
        return this.categoryDao.getCategoryById(id);
    }

    public List<Category> getAllCategorys() {
        // TODO Auto-generated method stub
        List<Category> getAllCategorys = categoryDao.getAllCategorys();
        return getAllCategorys;
    }

    public void addCategory(Category category){
        categoryDao.addCategory(category);
    }

    public void deleteCategoryById(int id){ categoryDao.deleteCategoryById(id);}

    public void updateCategory(Category category){
        categoryDao.updateCategory(category);
    }

}
