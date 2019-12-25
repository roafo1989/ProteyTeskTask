package com.example.demo1.controller;

import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import org.springframework.test.web.servlet.ResultMatcher;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.example.demo1.controller.TestUtil.readFromJsonMvcResult;
import static com.example.demo1.model.StatusOfEnable.AWAY;
import static com.example.demo1.model.StatusOfEnable.ONLINE;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int START_SEQ = 100000;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    static User USER = new User(
            USER_ID,
            "User",
            "user@yandex.ru",
            "password",
            "89110864162");
    static User ADMIN = new User(
            ADMIN_ID,
            "Admin",
            "admin@gmail.com",
            "admin",
            "8800200");
    static User USER2 = new User(
            null,
            "User2",
            "user2@yandex.ru",
            "password2",
            "89119119191");


    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered");
    }
    public static ResultMatcher contentJson(User expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, User.class), expected);
    }


}
