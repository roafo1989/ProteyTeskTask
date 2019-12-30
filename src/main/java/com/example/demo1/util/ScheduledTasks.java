package com.example.demo1.util;

import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ScheduledTasks {

    private static final long RATE = 1 * 60 * 1000; // 5 minutes

    private UserService userService;

    @Autowired
    public ScheduledTasks(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(fixedDelay = RATE)
    void updateStatus() {
        System.out.println("Change status users");
        userService.updateStatus();
    }
}
