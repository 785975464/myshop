package com.javen.dao;

import com.javen.model.User;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IUserDao {
    User selectUser(long id);
}
