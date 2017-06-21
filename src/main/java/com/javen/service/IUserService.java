package com.javen.service;

import com.javen.model.User;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IUserService {
    public User selectUser(long userId);
}
