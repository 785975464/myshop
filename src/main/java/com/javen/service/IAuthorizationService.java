package com.javen.service;

import com.javen.model.Authorization;

/**
 * Created by Jay on 2017/6/21.
 */
public interface IAuthorizationService extends IBaseService {

    Authorization getByRole(int role);

//    public Level getLevelById(int id);
//
//    public List<Level> getAllLevels();
//
//    public void addLevel(Level level);
//
//    public void deleteLevelById(int id);
//
//    public void updateLevel(Level level);

}
