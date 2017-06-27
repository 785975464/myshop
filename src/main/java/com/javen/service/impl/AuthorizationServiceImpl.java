package com.javen.service.impl;

import com.javen.dao.IAuthorizationDao;
import com.javen.dao.IBaseDao;
import com.javen.model.Authorization;
import com.javen.service.IAuthorizationService;
import com.javen.service.ILevelService;
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

//    @Resource
//    private ILevelDao levelDao;
//
//    public Level getLevelById(int id) {
//        return this.levelDao.getLevelById(id);
//    }
//
//    public List<Level> getAllLevels() {
//        // TODO Auto-generated method stub
//        List<Level> getAllLevels = levelDao.getAllLevels();
//        return getAllLevels;
//    }
//
//    public void addLevel(Level level){
//        levelDao.addLevel(level);
//    }
//
//    public void deleteLevelById(int id){ levelDao.deleteLevelById(id);}
//
//    public void updateLevel(Level category){
//        levelDao.updateLevel(category);
//    }

}
