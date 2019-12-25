package com.example.demo1.service;

import com.example.demo1.dao.UserRepo;
import com.example.demo1.model.StatusOfEnable;
import com.example.demo1.model.User;
import com.example.demo1.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.demo1.model.StatusOfEnable.AWAY;
import static com.example.demo1.util.StatusChanger.changeToAway;
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
        return userRepo.save(user);
    }

    public void update(User user){
        Assert.notNull(user,"user must be not null");
        userRepo.save(user);
    }

    public User getById(int id){
        return checkNotFoundWithId(userRepo.findById(id).orElse(null),id);
    }

    public void delete(int id){
        checkNotFoundWithId(userRepo.delete(id),id);
    }

    public void changeStatus(User user, StatusOfEnable current){
        Assert.notNull(user,"user must be not null");
        user.setEnabled(current);
        userRepo.save(user);
        if (current.equals(StatusOfEnable.ONLINE)){
            changeToAway(user);
            userRepo.save(user);
        }
    }

}
