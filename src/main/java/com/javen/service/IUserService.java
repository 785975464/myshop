package com.javen.service;

import com.javen.model.User;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IUserService {
    public User getUserById(int userId);

    public List<User> getAllUsers();

    public void addUser(User user);

}
