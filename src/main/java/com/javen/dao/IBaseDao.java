package com.javen.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IBaseDao<T,ID extends  Serializable> {

    void add(T t);

    void update(T t);

    void delete(ID id);

    T get(ID id);

    List<T> query();

}
