package com.javen.service;

import com.javen.model.Level;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Transactional
public interface ILevelService extends IBaseService {

    Level getDiscount(int level);

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
