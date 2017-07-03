package com.javen.service;

import com.javen.model.Level;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Jay on 2017/6/21.
 */
@Transactional
public interface ILevelService extends IBaseService {

    Level getDiscount(int level);

}
