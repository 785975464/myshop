package com.javen.dao;

import com.javen.model.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IBaseDao<T,ID extends  Serializable> {
//    public User getUserById(int id);
//
//    public List<User> getAllUsers();
//
//    public void addUser(User user);
//
//    public void deleteUserById(int id);
//
//    public void updateUser(User user);
    void add(T t);

    void update(T t);

    void delete(ID id);

    T get(ID id);

    List<T> query();

}
