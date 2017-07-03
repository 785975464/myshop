package com.javen.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Jay on 2017/7/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring-mybatis.xml"})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(),new UserControllerTest()).build();
    }

    @Test
    public void getUserMsg() throws Exception{
        this.mockMvc.perform(get("/user/get")
                .param("id","1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
