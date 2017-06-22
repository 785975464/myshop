package com.javen.dao;

import com.javen.model.Category;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface ICategoryDao {
    Category getCategoryById(int id);

    public List<Category> getAllCategorys();

    public void addCategory(Category category);
}
