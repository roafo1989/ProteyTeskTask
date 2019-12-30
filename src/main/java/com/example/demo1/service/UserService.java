package com.example.demo1.service;

import com.example.demo1.dao.UserRepo;
import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import com.example.demo1.util.StatusChanger;
import com.example.demo1.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.*;

import static com.example.demo1.util.ValidationUtil.checkNotFoundWithId;


@Service
public class UserService {
    private UserRepo userRepo;
    @Autowired
    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User create(User user){
        Assert.notNull(user,"user must be not null");
        if(user.getOnlineTime() == null){
            user.setOnlineTime(new Date());
        }
        if(user.getEnabled() == null){
            System.out.println("if from service");
            user.setEnabled(StatusOfEnable.ONLINE);
        }
        return userRepo.save(user);
    }

    public void update(User user){
        Assert.notNull(user,"user must be not null");
        user.setOnlineTime(new Date());
        userRepo.saveAndFlush(user);
    }

    public StatusResponse changeStatus(int id, String newStatus){
        StatusOfEnable current = StatusOfEnable.values()[Integer.parseInt(newStatus)];
        StatusResponse statusResponse = new StatusResponse();
        try {
            User user = checkNotFoundWithId(userRepo.findById(id).orElse(null),id);
            statusResponse.setOldStatus(user.getEnabled());
            statusResponse.setId(id);
            statusResponse.setCurrentStatus(current);
            user.setEnabled(current);
            user.setOnlineTime(new Date());
            userRepo.saveAndFlush(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusResponse;
    }

    public void updateStatus() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -1);
        Date calTime = cal.getTime();
        userRepo.updateStatus(calTime);
    }

    public User getById(int id){
        return checkNotFoundWithId(userRepo.findById(id).orElse(null),id);
    }

    public void delete(int id){
        checkNotFoundWithId(userRepo.delete(id),id);
    }

}
