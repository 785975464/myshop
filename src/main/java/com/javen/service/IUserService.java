package com.javen.service;

import com.javen.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Jay on 2017/6/21.
 */
@Transactional
public interface IUserService extends IBaseService{

    List<User> login(User user);

}
