package com.example.demo1.util;
import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.TimerTask;

@Getter
@Setter
public class StatusChanger extends TimerTask {
    private User user;
    private boolean trigger = false;

    public StatusChanger(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        user.setEnabled(StatusOfEnable.AWAY);

        System.out.println("in status changer: " + user.getEnabled());
    }
}
