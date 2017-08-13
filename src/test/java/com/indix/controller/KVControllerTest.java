package com.indix.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Madhav on 8/8/17.
 */



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:spring-dao.xml")
public class KVControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void putDataTest(){
        try{
            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/set/abc/");
            this.mockMvc.perform(builder.content("value")).andDo(print()).andExpect(status().isCreated());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getNullDataTest(){
        try{
            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/get/abc1");
            this.mockMvc.perform(builder).andDo(print()).andExpect(status().isNotFound());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void getDataTest(){
        try{
            MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/get/abc");
            this.mockMvc.perform(builder).andDo(print()).andExpect(status().isOk());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
