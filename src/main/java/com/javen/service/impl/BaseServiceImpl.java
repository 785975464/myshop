package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.service.IBaseService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("baseService")
public abstract class BaseServiceImpl<T,ID extends Serializable> implements IBaseService<T,ID> {

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

}
