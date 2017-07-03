package com.javen.service.impl;

import com.javen.dao.IBaseDao;
import com.javen.dao.ILevelDao;
import com.javen.model.Level;
import com.javen.service.ILevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
