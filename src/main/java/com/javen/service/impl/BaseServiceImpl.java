package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.IUserDao;
import com.javen.model.User;
import com.javen.service.IBaseService;
import com.javen.service.IUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("baseService")
//@Lazy(true)
public abstract class BaseServiceImpl<T,ID extends Serializable> implements IBaseService<T,ID> {

//    @Resource
//    private IUserDao userDao;
    public abstract IBaseDao getDao();

    public T get(ID id) {
        return (T)getDao().get(id);
    }

    public void add(T t){
        getDao().add(t);
    }

    public void update(T t){
        getDao().update(t);
    }

    public void delete(ID id){
        getDao().delete(id);
    }

    public List<T> query(){
        List<T> list = getDao().query();
        return list;
    }

//    public List<User> getAllUsers() {
//        // TODO Auto-generated method stub
//        List<User> getAllUsers = userDao.getAllUsers();
//        return getAllUsers;
//    }

//    public void add(T t){ userDao.add(t); }

//    public void deleteUserById(int id){ userDao.deleteUserById(id); }

//    public void updateUser(User user){ userDao.updateUser(user); }

}
