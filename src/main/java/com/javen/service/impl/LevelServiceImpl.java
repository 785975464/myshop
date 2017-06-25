package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.ICategoryDao;
import com.javen.dao.ILevelDao;
import com.javen.model.Category;
import com.javen.model.Level;
import com.javen.service.IBaseService;
import com.javen.service.ICategoryService;
import com.javen.service.ILevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Service("levelService")
public class LevelServiceImpl extends BaseServiceImpl implements ILevelService {

    @Autowired
    private ILevelDao levelDao;

    @Override
    public IBaseDao getDao(){
        return levelDao;
    }

    public Level getDiscount(int level){ return levelDao.getDiscount(level); }

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
