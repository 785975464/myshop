package com.javen.dao;

import com.javen.model.Level;

/**
 * Created by Jay on 2017/6/21.
 */
public interface ILevelDao extends IBaseDao<Level,Integer>{

    Level getDiscount(int level);

}
