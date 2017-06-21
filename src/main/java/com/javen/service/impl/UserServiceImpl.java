package com.javen.service.impl;

import com.javen.dao.IUserDao;
import com.javen.model.User;
import com.javen.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("userService")
public class UserServiceImpl  implements IUserService {

    @Resource
    private IUserDao userDao;

    public User selectUser(long userId) {
        return this.userDao.selectUser(userId);
    }

}
