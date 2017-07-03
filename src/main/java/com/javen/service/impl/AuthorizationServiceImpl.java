package com.javen.service.impl;

import com.javen.dao.IAuthorizationDao;
import com.javen.dao.IBaseDao;
import com.javen.model.Authorization;
import com.javen.service.IAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("authorizationService")
public class AuthorizationServiceImpl extends BaseServiceImpl implements IAuthorizationService {

    @Autowired
    private IAuthorizationDao authorizationDao;

    @Override
    public IBaseDao getDao(){
        return authorizationDao;
    }

    public Authorization getByRole(int role){return authorizationDao.getByRole(role);}

}
