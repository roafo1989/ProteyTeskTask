package com.example.demo1.util;

import com.example.demo1.model.User;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import static com.example.demo1.model.StatusOfEnable.AWAY;


@EnableScheduling
public class StatusChanger {

    @Scheduled(fixedDelay = 30000)
    public static void changeToAway(User user){
        user.setEnabled(AWAY);
    }
}
