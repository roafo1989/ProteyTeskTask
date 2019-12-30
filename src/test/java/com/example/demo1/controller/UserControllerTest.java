package com.example.demo1.controller;

import com.example.demo1.Demo1Application;
import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import com.example.demo1.service.UserServiceImpl;
import com.example.demo1.util.exception.NotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;


import static com.example.demo1.controller.TestUtil.readFromJson;
import static com.example.demo1.controller.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = Demo1Application.class)
class UserControllerTest {
    @Autowired
    private UserServiceImpl userServiceImpl;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    void postConstruct() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    static final String REST_URL = "http://localhost:8080/users";

    static final String URI_DEL = REST_URL + "/deleteUser/" + USER_ID;
    static final String URI_GET = REST_URL + "/get.by.id/" + USER_ID;
    static final String URI_UPD = REST_URL + "/updateUser/" + USER_ID;
    static final String CHANGE_STATUS = REST_URL + "/change.status/" + USER_ID;

    @Test
    void getById() throws Exception {
        mockMvc.perform(get(URI_GET))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
    }

    @Test
    void deleteUser() throws Exception {
        ResultActions result = mockMvc.perform(delete(URI_DEL));
        MockHttpServletResponse response = result.andReturn().getResponse();
        Assert.assertEquals("false",HttpServletResponse.SC_NO_CONTENT,response.getStatus());
        assertThrows(NotFoundException.class, () -> userServiceImpl.getUserById(USER_ID));
    }

    @Test
    void createUser() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(USER)))
                .andExpect(status().isCreated());

        User created = readFromJson(result, User.class);
        Integer newId = created.getId();
        USER.setId(newId);
        assertMatch(created, USER);
        assertMatch(userServiceImpl.getUserById(newId), USER);
    }

    @Test
    void updateUser() throws Exception {
        ResultActions result = mockMvc.perform(get(URI_GET))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(USER));
        User user = readFromJson(result, User.class);
        USER2.setId(user.getId());
        mockMvc.perform(MockMvcRequestBuilders.put(URI_UPD)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(USER2)))
                .andExpect(status().isNoContent());
        assertMatch(userServiceImpl.getUserById(USER_ID), USER2);
    }

    @Test
    void changeStatus() throws Exception {
        ResultActions result = mockMvc.perform(get(URI_GET))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User user = readFromJson(result, User.class);

        StatusOfEnable updateStatus = StatusOfEnable.ONLINE;

        user.setEnabled(updateStatus);

        mockMvc.perform(put(CHANGE_STATUS)
                .param("enabled", updateStatus + ""))
                .andReturn();

        assertMatch(userServiceImpl.getUserById(USER_ID),user);
    }

   /* @Test
    void changeStatusToAwayAfterDelay() throws Exception {
        ResultActions result = mockMvc.perform(get(URI_GET))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        User user = readFromJson(result, User.class);
        StatusOfEnable updateStatus = StatusOfEnable.ONLINE;
        user.setEnabled(updateStatus);
        mockMvc.perform(put(CHANGE_STATUS)
                .param("enabled", updateStatus + ""))
                .andReturn();
        change(user);

        Thread.sleep(30000);
        updateStatus = user.getEnabled();
        mockMvc.perform(put(CHANGE_STATUS)
                .param("enabled", updateStatus + ""))
                .andReturn();

        assertMatch(userService.getUserById(USER_ID),user);
    }
*/
    @Test
    void getAll() throws Exception {

    }
}