package com.javen.dao;

import com.javen.model.User;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IUserDao extends IBaseDao<User,Integer>{

    List<User> login(User user);

}
