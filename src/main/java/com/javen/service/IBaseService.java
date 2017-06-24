package com.javen.service;

import com.javen.model.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IBaseService<T,ID extends Serializable> {
//    public User getUserById(int userId);
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
