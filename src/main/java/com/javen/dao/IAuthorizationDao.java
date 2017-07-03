package com.javen.dao;

import com.javen.model.Authorization;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IAuthorizationDao extends IBaseDao<Authorization,Integer>{

    Authorization getByRole(int role);

}
