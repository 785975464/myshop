package com.javen.service.impl;

import com.javen.dao.IUserDao;
import com.javen.model.User;
import com.javen.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("userService")
public class UserServiceImpl  implements IUserService {

    @Resource
    private IUserDao userDao;

    public User getUserById(int userId) {
        return this.userDao.getUserById(userId);
    }

    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        List<User> getAllUsers = userDao.getAllUsers();
        return getAllUsers;
    }

    public void addUser(User user){
        userDao.addUser(user);
    }

}
