package com.javen.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Transactional
public interface IBaseService<T,ID extends Serializable> {

    void add(T t);

    void update(T t);

    void delete(ID id);

    T get(ID id);

    List<T> query();
}
