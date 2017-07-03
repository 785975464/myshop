package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.IUserDao;
import com.javen.model.User;
import com.javen.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public IBaseDao getDao(){
        return userDao;
    }

    public List<User> login(User user){
        return userDao.login(user);
    }

}
