package com.javen.service.impl;

import com.javen.model.User;
import com.javen.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Jay on 2017/7/3.
 */
@TransactionConfiguration(defaultRollback = true)       //开启事物，自动回滚
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Test
    public void serviceGetTest(){
        User user = (User)userService.get(10);
        System.out.println("serviceTest user:"+user);
    }

    @Test
    public void serviceAddTest(){
        User user = new User();
        user.setUsername("serviceTestUser回滚测试123");
        user.setPassword("password");
        user.setBirthday(new Date());
        user.setGender("未知");
        user.setPhone("phone");
        user.setEmail("email");
        user.setRole(1);
        user.setLevel(0);
        user.setIsdeleted(false);
        userService.add(user);
        System.out.println("serviceTest user:"+user);
    }

    @Test
    public void serviceDeleteTest(){
        userService.delete(22);
        System.out.println("serviceDeleteTest user");
    }
}
